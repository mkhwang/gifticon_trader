package com.example.gifticon_trader.unit.user.application;


import com.example.gifticon_trader.user.application.VerificationEmailSender;
import com.example.gifticon_trader.user.application.VerificationTokenGenerator;
import com.example.gifticon_trader.user.application.VerificationTokenService;
import com.example.gifticon_trader.user.application.VerifyEmailResult;
import com.example.gifticon_trader.user.application.exception.InvalidTokenException;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.VerificationToken;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.user.infra.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceTest {
  @Mock
  UserRepository userRepository;
  @Mock
  VerificationEmailSender emailSender;
  @Mock
  VerificationTokenRepository tokenRepository;
  @Mock
  VerificationTokenGenerator tokenGenerator;

  @InjectMocks
  VerificationTokenService service;

  @Test
  void process_shouldReturnAlreadyVerified_ifUserIsActive() {
    // given
    User user = mock(User.class);
    given(userRepository.findByUsername("test@example.com")).willReturn(Optional.of(user));
    given(user.isActive()).willReturn(true);

    // when
    VerifyEmailResult result = service.process("test@example.com");

    // then
    assertThat(result).isEqualTo(VerifyEmailResult.ALREADY_VERIFIED);
  }

  @Test
  void process_shouldSendEmailAndReturnEmailSent_ifNoTokenExists() {
    User user = mock(User.class);
    given(userRepository.findByUsername("test@example.com")).willReturn(Optional.of(user));
    given(user.isActive()).willReturn(false);
    given(tokenRepository.findVerificationTokensByUser(user)).willReturn(Optional.empty());

    VerificationToken token = mock(VerificationToken.class);
    given(tokenGenerator.generate()).willReturn("TOKEN");
    given(user.getUsername()).willReturn("test@example.com");

    // when
    VerifyEmailResult result = service.process("test@example.com");

    // then
    verify(emailSender).send(user.getUsername(), "TOKEN");
    verify(tokenRepository).save(any());
    assertThat(result).isEqualTo(VerifyEmailResult.EMAIL_SENT);
  }

  @Test
  void verifyEmailToken_shouldThrow_ifTokenNotFound() {
    given(tokenRepository.findByToken("abc")).willReturn(Optional.empty());
    assertThatThrownBy(() -> service.verifyEmailToken("abc"))
            .isInstanceOf(InvalidTokenException.class);
  }

  @Test
  void verifyEmailToken_shouldDeleteTokenAndResendEmail_ifExpired() {
    User user = mock(User.class);
    VerificationToken token = mock(VerificationToken.class);
    given(tokenRepository.findByToken("abc")).willReturn(Optional.of(token));
    given(token.isExpired()).willReturn(true);
    given(token.getUser()).willReturn(user);
    given(user.getUsername()).willReturn("test@example.com");
    given(tokenGenerator.generate()).willReturn("newToken");

    service.verifyEmailToken("abc");

    verify(tokenRepository).delete(token);
    verify(tokenRepository).save(any());
    verify(emailSender).send(eq("test@example.com"), eq("newToken"));
  }

  @Test
  void verifyEmailToken_shouldUpdateUserAndDeleteToken_ifValid() {
    User user = mock(User.class);
    VerificationToken token = mock(VerificationToken.class);
    given(tokenRepository.findByToken("abc")).willReturn(Optional.of(token));
    given(token.isExpired()).willReturn(false);
    given(token.getUser()).willReturn(user);

    service.verifyEmailToken("abc");

    verify(user).emailVerified();
    verify(tokenRepository).delete(token);
  }
}