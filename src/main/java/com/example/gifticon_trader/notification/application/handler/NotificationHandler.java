package com.example.gifticon_trader.notification.application.handler;

import com.example.gifticon_trader.notification.domain.NotificationType;

public interface NotificationHandler {
  boolean supports(NotificationType type);
  void send(long notificationDeliveryId);
}
