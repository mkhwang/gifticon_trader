package com.example.gifticon_trader.notification.application.resolver.payload.email;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WelcomeEmailNotificationResolver implements EmailNotificationPayloadResolver {

  @Override
  public boolean supports(NotificationType notificationType) {
    return notificationType == NotificationType.WELCOME;
  }

  @Override
  public String getTemplate() {
    return "welcome_template.html";
  }

  @Override
  public NotificationPayload resolve(NotificationLogRecord notificationLogRecord) {
    return new EmailPayload(
            notificationLogRecord.title(),
            this.getTemplate(),
            Map.of()
    );
  }
}
