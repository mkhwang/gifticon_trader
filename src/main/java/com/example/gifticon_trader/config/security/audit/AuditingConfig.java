package com.example.gifticon_trader.config.security.audit;

import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class AuditingConfig {
  private final UserRepository userRepository;

  @Bean
  public AuditorAware<User> auditorProvider() {
    return new AuditorAwareImpl(this.userRepository);
  }
}

