package com.example.gifticon_trader.notification.application.resolver.message;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.user.domain.User;

public interface NotificationLogResolver {

  boolean supports(NotificationType type);
  NotificationLogRecord resolve(User user, NotificationType type, Long referenceId);
}
