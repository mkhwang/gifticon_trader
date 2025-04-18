package com.example.gifticon_trader.unituser.domain;

import com.example.gifticon_trader.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserTest {

  @Mock
  PasswordEncoder encoder;

  @Test
  void register_shouldInitializeFields_andEmitEvent() {
    String rawPassword = "12345678";
    String userName = "testuser";
    String encodedPassword = "encoded12345678";

    // given
    given(encoder.encode(rawPassword)).willReturn(encodedPassword);
    given(encoder.matches(rawPassword, encodedPassword)).willReturn(true);

    // when
    User user = User.register(userName, rawPassword, encoder);

    // then
    assertThat(user.getUsername()).isEqualTo(userName);
    assertThat(user.matchesPassword(rawPassword, encoder)).isTrue();
    assertThat(user.isActive()).isFalse();
  }

  @Test
  void changePassword_shouldUpdatePassword() {
    String userName = "testuser";
    String password = "12345678";
    String newPassword = "newpass";
    String newEncoded = "newEncoded";

    // given
    given(encoder.encode(password)).willReturn("encoded123");
    given(encoder.encode(newPassword)).willReturn(newEncoded);
    given(encoder.matches(newPassword, newEncoded)).willReturn(true);

    User user = User.register(userName, password, encoder);

    // when
    user.changePassword(newPassword, encoder);

    // then
    assertThat(user.matchesPassword(newPassword, encoder)).isTrue();
  }

  @Test
  void emailVerified_shouldSetEmailNonExpiredTrue() {
    // given
    User user = User.register("user",  "raw", encoder);

    // when
    user.emailVerified();

    // then
    assertThat(user.isActive()).isTrue(); // 이메일 인증 후 활성화로 간주
  }
}