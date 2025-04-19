package com.example.gifticon_trader.notification.application.resolver.message;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationMessage;
import com.example.gifticon_trader.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class WelcomeNotificationMessageResolver implements NotificationMessageResolver {
  @Override
  public boolean supports(NotificationType type) {
    return type == NotificationType.WELCOME;
  }

  @Override
  public NotificationMessage resolve(User user, NotificationType type) {
    String title = "환영합니다, %s님!".formatted(user.getNickname());
    String message = "기프티콘 거래소에 가입해주셔서 감사합니다";
    return new NotificationMessage(title, message);
  }
}
