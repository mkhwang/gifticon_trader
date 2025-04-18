package com.example.gifticon_trader.config.security;

import com.example.gifticon_trader.config.security.authority.application.AuthorityService;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  private final AuthorityService authorityService;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User existUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return existUser.toLoginUser(this.authorityService.getAuthoritiesByUser(existUser.getId()));
  }
}
