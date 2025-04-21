package com.example.gifticon_trader.user.in.rest.dto;

import com.example.gifticon_trader.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto extends PageRequestDto {
  private String nickname;
}
