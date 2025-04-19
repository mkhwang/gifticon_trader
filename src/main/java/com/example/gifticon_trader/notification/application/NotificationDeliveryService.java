package com.example.gifticon_trader.notification.application;

import com.example.gifticon_trader.notification.application.exception.NotificationDeliveryNotFoundException;
import com.example.gifticon_trader.notification.application.handler.NotificationHandler;
import com.example.gifticon_trader.notification.application.handler.NotificationHandlerFactory;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationDeliveryService {
  private final NotificationHandlerFactory notificationHandlerFactory;
  private final NotificationDeliveryLogRepository notificationDeliveryLogRepository;

  @Transactional
  public void process(Long deliveryId) {

    NotificationDeliveryLog deliveryLog = notificationDeliveryLogRepository.findById(deliveryId)
            .orElseThrow(NotificationDeliveryNotFoundException::new);

    NotificationHandler handler = notificationHandlerFactory
            .getHandler(deliveryLog.getNotificationLog().getNotificationType());
    try {
      handler.send(deliveryLog.getId());
      deliveryLog.markSent();
    } catch (Exception ignore) {
      // TODO: Implement retry logic
    }
  }
}
