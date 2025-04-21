package com.example.gifticon_trader.gifticon.domain.command;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GifticonRegisterCommand {
  private final String name;
  private final String description;
  private final Long originalPrice;
  private final Long price;
  private final LocalDate expirationDate;
  private final float discountRate;

  public GifticonRegisterCommand(String name, String description, Long originalPrice, Long price, LocalDate expirationDate) {
    this.name = name;
    this.description = description;
    this.originalPrice = originalPrice;
    this.price = price;
    this.expirationDate = expirationDate;
    this.discountRate = (float) (originalPrice - price) / originalPrice * 100;
  }
}
