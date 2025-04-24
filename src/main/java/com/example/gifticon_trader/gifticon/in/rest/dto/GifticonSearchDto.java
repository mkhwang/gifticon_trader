package com.example.gifticon_trader.gifticon.in.rest.dto;


import com.example.gifticon_trader.common.dto.PageRequestDto;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GifticonSearchDto extends PageRequestDto {

  private String keyword;
  private GifticonStatus status;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long sellerId;
}
