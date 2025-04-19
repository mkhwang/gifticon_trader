package com.example.gifticon_trader.notification.application.resolver.message;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class WelcomeNotificationLogResolver implements NotificationLogResolver {
  @Override
  public boolean supports(NotificationType type) {
    return type == NotificationType.WELCOME;
  }

  @Override
  public NotificationLogRecord resolve(User user, NotificationType type, Long referenceId) {
    String title = "환영합니다, %s님!".formatted(user.getNickname());
    String message = "기프티콘 거래소에 가입해주셔서 감사합니다";
    return new NotificationLogRecord(title, message, NotificationType.WELCOME, null);
  }
}
