package com.example.gifticon_trader.notification.application.resolver.message;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class EmailVerifyNotificationLogResolver implements NotificationLogResolver {
  @Override
  public boolean supports(NotificationType type) {
    return type == NotificationType.VERIFY_EMAIL;
  }

  @Override
  public NotificationLogRecord resolve(User user, NotificationType type, Long referenceId) {
    String title = "안녕하세요, %s님!".formatted(user.getNickname());
    String message = "이메일 인증을 위해 아래 링크를 클릭해주세요.";
    return new NotificationLogRecord(title, message, NotificationType.VERIFY_EMAIL, referenceId);
  }
}
