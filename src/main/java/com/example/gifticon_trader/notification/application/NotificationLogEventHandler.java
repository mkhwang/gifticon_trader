package com.example.gifticon_trader.notification.application;

import com.example.gifticon_trader.common.message.publisher.MessagePublisher;
import com.example.gifticon_trader.config.mesage.MessageConstants;
import com.example.gifticon_trader.notification.application.exception.NotificationLogNotFoundException;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.event.NotificationCreatedEvent;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import com.example.gifticon_trader.notification.infra.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationLogEventHandler {
  private final NotificationLogRepository notificationLogRepository;
  private final NotificationDeliveryLogRepository deliveryLogRepository;
  private final MessagePublisher messagePublisher;

  @TransactionalEventListener
  public void handleNotificationLogEvent(NotificationCreatedEvent event) {
    NotificationLog log = notificationLogRepository.findById(event.id()).orElseThrow(
            NotificationLogNotFoundException::new
    );

    for (NotificationChannel channel : log.getNotificationType().getSupportedChannels()) {
      NotificationDeliveryLog delivery = NotificationDeliveryLog.create(log, channel);
      deliveryLogRepository.save(delivery);

      messagePublisher.publish(MessageConstants.NOTIFICATION_TOPIC, delivery.getId());
    }
  }
}
