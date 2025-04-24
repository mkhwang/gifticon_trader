package com.example.gifticon_trader.unit.notification.application.resolver.payload.email;

import com.example.gifticon_trader.notification.application.resolver.payload.email.VerifyEmailNotificationResolver;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import com.example.gifticon_trader.user.domain.VerificationToken;
import com.example.gifticon_trader.user.infra.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class VerifyEmailNotificationResolverTest {
  @Mock
  VerificationTokenRepository verificationTokenRepository;

  @InjectMocks
  VerifyEmailNotificationResolver verifyEmailNotificationResolver;

  @Test
  void supports() {
    // given
    NotificationType notificationType = NotificationType.VERIFY_EMAIL;

    // when
    boolean result = verifyEmailNotificationResolver.supports(notificationType);

    // then
    assertTrue(result);
  }


  @Test
  void getTemplate() {
    // given
    String expectedTemplate = "email-verification.html";

    // when
    String result = verifyEmailNotificationResolver.getTemplate();

    // then
    assertEquals(expectedTemplate, result);
  }

  @Test
  void resolve() {
    // given
    String expectedTemplate = "email-verification.html";
    String expectedTitle = "title";
    Long expectedReferenceId = 1L;
    VerificationToken verificationToken = mock(VerificationToken.class);

    given(verificationToken.getUsername()).willReturn("username");
    given(verificationToken.getToken()).willReturn("token");
    given(verificationTokenRepository.findById(expectedReferenceId)).willReturn(Optional.of(verificationToken));

    // when
    NotificationPayload result = verifyEmailNotificationResolver.resolve(
            new NotificationLogRecord(expectedTitle, null, NotificationType.VERIFY_EMAIL, expectedReferenceId)
    );

    // then
    assertInstanceOf(EmailPayload.class, result);
    EmailPayload emailPayload = (EmailPayload) result;
    assertEquals(expectedTitle, emailPayload.getSubject());
    assertEquals(expectedTemplate, emailPayload.getTemplateName());
  }
}