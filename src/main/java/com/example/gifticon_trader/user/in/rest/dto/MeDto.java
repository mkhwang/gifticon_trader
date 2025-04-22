package com.example.gifticon_trader.user.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeDto {

  private Long id;
  private String username;
  private String nickname;
  private List<String> authorities;
  private boolean enabled;
}
