package com.example.gifticon_trader.user.in.rest.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
  private Long id;
  private String nickname;

  @QueryProjection
  public UserDto(Long id, String nickname) {
    this.id = id;
    this.nickname = nickname;
  }
}
