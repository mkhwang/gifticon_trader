package com.example.gifticon_trader.notification.application.resolver.message;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationMessage;
import com.example.gifticon_trader.user.domain.User;

public interface NotificationMessageResolver {

  boolean supports(NotificationType type);
  NotificationMessage resolve(User user, NotificationType type);
}
