package com.example.gifticon_trader.notification.application;

import com.example.gifticon_trader.common.message.publisher.MessagePublisher;
import com.example.gifticon_trader.config.mesage.MessageConstants;
import com.example.gifticon_trader.notification.application.exception.NotSupportedNotificationType;
import com.example.gifticon_trader.notification.application.exception.NotificationLogNotFoundException;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.application.resolver.payload.NotificationPayloadAssembler;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.event.NotificationCreatedEvent;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import com.example.gifticon_trader.notification.infra.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationLogEventHandler {
  private final NotificationLogRepository notificationLogRepository;
  private final NotificationDeliveryLogRepository deliveryLogRepository;
  private final List<NotificationLogResolver> notificationLogResolvers;
  private final List<NotificationPayloadAssembler> notificationPayloadAssemblers;
  private final MessagePublisher messagePublisher;

  @Transactional
  @TransactionalEventListener
  public void handleNotificationLogEvent(NotificationCreatedEvent event) {
    NotificationLog log = notificationLogRepository.findById(event.id()).orElseThrow(
            NotificationLogNotFoundException::new
    );

    NotificationLogRecord logRecord = notificationLogResolvers.stream()
            .filter(resolver -> resolver.supports(log.getNotificationType()))
            .findFirst()
            .orElseThrow(NotSupportedNotificationType::new)
            .resolve(log.getUser(), log.getNotificationType(), log.getReferenceId());


    for (NotificationChannel channel : log.getNotificationChannels()) {
      NotificationPayload payload = notificationPayloadAssemblers.stream()
              .filter(assembler -> assembler.supports(channel))
              .findFirst()
              .orElseThrow(NotSupportedNotificationType::new)
              .assemble(logRecord);

      NotificationDeliveryLog delivery = NotificationDeliveryLog.create(log, channel, payload);
      deliveryLogRepository.save(delivery);

      messagePublisher.publish(MessageConstants.NOTIFICATION_TOPIC, delivery.getId());
    }
  }
}
