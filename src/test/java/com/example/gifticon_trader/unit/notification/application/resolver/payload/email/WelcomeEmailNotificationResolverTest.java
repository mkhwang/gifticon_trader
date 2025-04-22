package com.example.gifticon_trader.unit.notification.application.resolver.payload.email;

import com.example.gifticon_trader.notification.application.resolver.payload.email.WelcomeEmailNotificationResolver;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeEmailNotificationResolverTest {
  @InjectMocks
  WelcomeEmailNotificationResolver welcomeEmailNotificationResolver;

  @BeforeEach
  void setUp() {
    welcomeEmailNotificationResolver = new WelcomeEmailNotificationResolver();
  }

  @Test
  void supports() {
    // given
    var notificationType = com.example.gifticon_trader.notification.domain.NotificationType.WELCOME;

    // when
    boolean result = welcomeEmailNotificationResolver.supports(notificationType);

    // then
    assertTrue(result);
  }

  @Test
  void getTemplate() {
    // given
    String expectedTemplate = "welcome_template.html";

    // when
    String result = welcomeEmailNotificationResolver.getTemplate();

    // then
    assertEquals(expectedTemplate, result);
  }

  @Test
  void resolve() {
    // given
    String expectedTemplate = "welcome_template.html";
    String expectedTitle = "title";
    Long expectedReferenceId = 1L;

    var notificationLogRecord = new com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord(
            expectedTitle,
            "message",
            com.example.gifticon_trader.notification.domain.NotificationType.WELCOME,
            expectedReferenceId
    );

    // when
    NotificationPayload result = welcomeEmailNotificationResolver.resolve(notificationLogRecord);

    // then
    assertInstanceOf(EmailPayload.class, result);
    EmailPayload emailPayload = (EmailPayload) result;
    assertEquals(expectedTitle, emailPayload.getSubject());
    assertEquals(expectedTemplate, emailPayload.getTemplateName());
  }
}