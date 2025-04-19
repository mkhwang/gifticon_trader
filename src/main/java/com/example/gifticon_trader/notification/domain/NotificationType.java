package com.example.gifticon_trader.notification.domain;

import java.util.Set;

public enum NotificationType {
  WELCOME(NotificationChannel.EMAIL),
  VERIFY_EMAIL(NotificationChannel.EMAIL),
  ADMIN_APPROVED(NotificationChannel.EMAIL, NotificationChannel.FCM),
  ADMIN_REJECTED(NotificationChannel.EMAIL, NotificationChannel.FCM),
  PAYMENT_COMPLETED(NotificationChannel.EMAIL, NotificationChannel.FCM),
  GIFTCON_RECEIVED(NotificationChannel.EMAIL, NotificationChannel.FCM),
  SETTLEMENT_COMPLETE(NotificationChannel.EMAIL, NotificationChannel.FCM);

  private final Set<NotificationChannel> supportedChannels;

  NotificationType(NotificationChannel... channels) {
    this.supportedChannels = Set.of(channels);
  }

  public boolean supports(NotificationChannel channel) {
    return supportedChannels.contains(channel);
  }

  public Set<NotificationChannel> getSupportedChannels() {
    return supportedChannels;
  }
}
