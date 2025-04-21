package com.example.gifticon_trader.unit.notification.application.resolver.message;

import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.application.resolver.message.WelcomeNotificationLogResolver;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WelcomeNotificationLogResolverUnitTest {

  NotificationLogResolver resolver = new WelcomeNotificationLogResolver();

  @Test
  void supports_shouldReturnTrueOnlyForWelcomeType() {
    assertThat(resolver.supports(NotificationType.WELCOME)).isTrue();
    assertThat(resolver.supports(NotificationType.VERIFY_EMAIL)).isFalse();
  }

  @Test
  void resolve_shouldReturnProperTitleAndMessage() {
    // given
    User user = mock(User.class);
    given(user.getNickname()).willReturn("user");

    // when
    NotificationLogRecord record = resolver.resolve(user, NotificationType.WELCOME, null);

    // then
    assertThat(record.title()).isEqualTo("환영합니다, user님!");
    assertThat(record.message()).isEqualTo("기프티콘 거래소에 가입해주셔서 감사합니다");
    assertThat(record.notificationType()).isEqualTo(NotificationType.WELCOME);
    assertThat(record.targetId()).isNull();
  }

}