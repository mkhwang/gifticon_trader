package com.example.gifticon_trader.gifticon.in.rest.dto;

import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GifticonDto {
  private Long id;
  private String name;
  private String description;
  private Long originalPrice;
  private Long price;
  private LocalDate expirationDate;
  private float discountRate;
  private GifticonStatus status;
  private UserDto createdBy;
  private Instant createdDate;
  private Instant lastModifiedDate;


  @QueryProjection
  public GifticonDto(Long id, String name, String description, Long originalPrice, Long price,
                     LocalDate expirationDate, float discountRate, GifticonStatus status, UserDto createdBy,
                     Instant createdDate, Instant lastModifiedDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.originalPrice = originalPrice;
    this.price = price;
    this.expirationDate = expirationDate;
    this.discountRate = discountRate;
    this.status = status;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }
}
