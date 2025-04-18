package com.example.gifticon_trader.config.security.audit;

import com.example.gifticon_trader.user.domain.LoginUser;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<User> {
  private final UserRepository userRepository;

  @SuppressWarnings("null")
  @Override
  public Optional<User> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }
    Object principal = authentication.getPrincipal();
    if (principal == null) {
      return Optional.empty();
    }
    LoginUser loginUser = (LoginUser) principal;
    return this.userRepository.findById(loginUser.getId());

  }
}
