package com.example.gifticon_trader.unit.notification.application.resolver.message;

import com.example.gifticon_trader.notification.application.resolver.message.EmailVerifyNotificationLogResolver;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailVerifyNotificationLogResolverTest {

  NotificationLogResolver resolver = new EmailVerifyNotificationLogResolver();

  @Test
  void supports_shouldReturnTrueOnlyForEmailVerifyType() {
    assertTrue(resolver.supports(NotificationType.VERIFY_EMAIL));
    assertFalse(resolver.supports(NotificationType.WELCOME));
  }

  @Test
  void resolve_shouldReturnProperTitleAndMessage() {
    // given
    User user = mock(User.class);
    given(user.getNickname()).willReturn("user");

    // when
    NotificationLogRecord record = resolver.resolve(user, NotificationType.VERIFY_EMAIL, null);

    // then
    assertEquals("안녕하세요, %s님!".formatted("user"), record.title());
    assertEquals("이메일 인증을 위해 아래 링크를 클릭해주세요.", record.message());
    assertEquals(NotificationType.VERIFY_EMAIL, record.notificationType());
    assertNull(record.targetId());
  }
}