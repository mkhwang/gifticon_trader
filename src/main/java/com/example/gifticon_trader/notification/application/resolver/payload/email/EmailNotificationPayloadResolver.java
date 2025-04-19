package com.example.gifticon_trader.notification.application.resolver.payload.email;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;

public interface EmailNotificationPayloadResolver {

  boolean supports(NotificationType notificationType);

  String getTemplate();

  NotificationPayload resolve(NotificationLogRecord notificationLogRecord);
}
