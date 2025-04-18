package com.example.gifticon_trader.config.security.authority.application;

import com.example.gifticon_trader.config.security.authority.infra.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorityService {
  private final AuthorityRepository authorityRepository;

  public Set<SimpleGrantedAuthority> getAuthoritiesByUser(Long userId) {
    return this.authorityRepository.findByUserId(userId).stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
            .collect(Collectors.toSet());
  }
}
