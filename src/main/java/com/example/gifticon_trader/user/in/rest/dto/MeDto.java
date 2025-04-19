package com.example.gifticon_trader.user.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeDto {

  private Long id;
  private String username;
  private String nickname;
  private Collection<? extends GrantedAuthority> authorities;
  private boolean enabled;
}
