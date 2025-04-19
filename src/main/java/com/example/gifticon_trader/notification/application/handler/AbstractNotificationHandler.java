package com.example.gifticon_trader.notification.application.handler;

import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationType;

public abstract class AbstractNotificationHandler implements NotificationHandler {

  protected abstract NotificationChannel getChannel();

  @Override
  public boolean supports(NotificationType type) {
    return type.supports(getChannel());
  }

  @Override
  public void send(long notificationDeliveryId) {
  }
}
