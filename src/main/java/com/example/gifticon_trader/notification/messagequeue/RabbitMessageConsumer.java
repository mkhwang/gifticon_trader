package com.example.gifticon_trader.notification.messagequeue;

import com.example.gifticon_trader.config.mesage.ConditionalOnMessageBroker;
import com.example.gifticon_trader.config.mesage.MessageBrokerType;
import com.example.gifticon_trader.config.mesage.MessageConstants;
import com.example.gifticon_trader.notification.application.NotificationDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnMessageBroker(MessageBrokerType.RABBIT)
@Component
@RequiredArgsConstructor
public class RabbitMessageConsumer {
  private final NotificationDeliveryService notificationDeliveryService;

  @RabbitListener(queues = MessageConstants.NOTIFICATION_TOPIC)
  public void consumeNotificationDeliveryLog(Long deliveryLogId) {
    notificationDeliveryService.process(deliveryLogId);
  }
}
