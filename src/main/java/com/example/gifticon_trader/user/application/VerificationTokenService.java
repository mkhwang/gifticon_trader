package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.user.application.exception.InvalidTokenException;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.VerificationToken;
import com.example.gifticon_trader.user.domain.event.VerificationEmailEvent;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.user.infra.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final VerificationTokenGenerator verificationTokenGenerator;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public VerifyEmailResult process(String email) {
    User existUser = userRepository.findByUsername(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (existUser.isActive()) {
      return VerifyEmailResult.ALREADY_VERIFIED;
    }

    return verificationTokenRepository.findVerificationTokensByUser(existUser)
        .map(token -> {
          if (token.isExpired()) {
            verificationTokenRepository.delete(token);
            return getVerifyEmailResult(existUser);
          }
          return VerifyEmailResult.NEED_VERIFICATION;
        })
        .orElseGet(() -> getVerifyEmailResult(existUser));
  }

  private VerifyEmailResult getVerifyEmailResult(User existUser) {
    VerificationToken token = VerificationToken.issueFor(existUser, verificationTokenGenerator);
    verificationTokenRepository.save(token);
    eventPublisher.publishEvent(new VerificationEmailEvent(existUser.getId(), token.getId()));
    return VerifyEmailResult.EMAIL_SENT;
  }


  @Transactional
  public void verifyEmailToken(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
        .orElseThrow(InvalidTokenException::new);

    if (verificationToken.isExpired()) {
      this.handleExpiredToken(verificationToken.getUser());
      verificationTokenRepository.delete(verificationToken);
      return;
    }

    User user = verificationToken.getUser();
    user.emailVerified();
    verificationTokenRepository.delete(verificationToken);
  }

  private void handleExpiredToken(User user) {
    this.getVerifyEmailResult(user);
  }
}
