package com.example.gifticon_trader.gifticon.in.rest.dto;

import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GifticonDto {
  private Long id;
  private String name;
  private String description;
  private Long originalPrice;
  private Long price;
  private String expirationDate;
  private float discountRate;
  private GifticonStatus status;
}
