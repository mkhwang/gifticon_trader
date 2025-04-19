package com.example.gifticon_trader.integration.user.application;

import com.example.gifticon_trader.user.application.VerificationTokenGenerator;
import com.example.gifticon_trader.user.application.VerificationTokenService;
import com.example.gifticon_trader.user.application.VerifyEmailResult;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.VerificationToken;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.user.infra.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class VerificationTokenServiceIntegrationTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  VerificationTokenService verificationTokenService;
  @Autowired
  VerificationTokenGenerator verificationTokenGenerator;
  @Autowired
  VerificationTokenRepository verificationTokenRepository;
  @Autowired
  PasswordEncoder passwordEncoder;


  @Test
  void process_shouldCreateTokenAndSendEmail_ifUsreExistsAndNoToken() {
    User testUser = User.register("test2@mail.com", "test2", "password", passwordEncoder);
    userRepository.save(testUser);

    VerifyEmailResult processResult = verificationTokenService.process("test2@mail.com");
    assertThat(processResult).isEqualTo(VerifyEmailResult.EMAIL_SENT);
    VerificationToken verificationToken = verificationTokenRepository.findVerificationTokensByUser(testUser)
            .orElse(null);
    assertThat(verificationToken).isNotNull();
    assertThat(verificationToken.getUser()).isEqualTo(testUser);
  }

  @Test
  void verifyEmailToken_shouldActivateUserAndDeleteToken() {
    // given
    User user = User.register("test@example.com", "test", "password", passwordEncoder);
    userRepository.save(user);

    VerificationToken token = VerificationToken.issueFor(user, verificationTokenGenerator);
    verificationTokenRepository.save(token);

    // when
    verificationTokenService.verifyEmailToken(token.getToken());

    // then
    User reloaded = userRepository.findByUsername("test@example.com").orElseThrow();
    assertThat(reloaded.isActive()).isTrue();

    VerificationToken stillExists = verificationTokenRepository.findByToken(token.getToken()).orElse(null);
    assertThat(stillExists).isNull();
  }

}