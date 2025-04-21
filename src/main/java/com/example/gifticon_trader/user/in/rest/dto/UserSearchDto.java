package com.example.gifticon_trader.user.in.rest.dto;

import com.example.gifticon_trader.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class UserSearchDto extends PageRequestDto {
  private String nickname;

  @Override
  public Pageable toPageable() {
    if (getSort() == null || getSort().isEmpty()) {
      setSort(List.of("nickname,asc")); // 기본 정렬
    }
    return super.toPageable();
  }
}
