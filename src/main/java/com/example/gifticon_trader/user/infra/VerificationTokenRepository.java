package com.example.gifticon_trader.user.infra;

import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
  Optional<VerificationToken> findVerificationTokensByUser(User existUser);

  Optional<VerificationToken> findByToken(String token);
}
