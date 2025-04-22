package com.example.gifticon_trader.notification.application.resolver.payload.email;

import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import com.example.gifticon_trader.user.application.exception.TokenNotFoundException;
import com.example.gifticon_trader.user.domain.VerificationToken;
import com.example.gifticon_trader.user.infra.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VerifyEmailNotificationResolver implements EmailNotificationPayloadResolver {

  private final VerificationTokenRepository verificationTokenRepository;

  @Override
  public boolean supports(NotificationType notificationType) {
    return notificationType == NotificationType.VERIFY_EMAIL;
  }

  @Override
  public String getTemplate() {
    return "email-verification.html";
  }

  @Override
  public NotificationPayload resolve(NotificationLogRecord notificationLogRecord) {

    VerificationToken existToken = verificationTokenRepository.findById(notificationLogRecord.referenceId())
            .orElseThrow(TokenNotFoundException::new);

    String verificationLink = "http://localhost:8080/verify-email-complete?token=" + existToken.getToken();
    String username = existToken.getUsername();

    return new EmailPayload(
            notificationLogRecord.title(),
            this.getTemplate(),
            Map.of("verificationLink", verificationLink, "username", username)
    );
  }
}
