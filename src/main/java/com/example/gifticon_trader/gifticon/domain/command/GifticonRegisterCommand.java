package com.example.gifticon_trader.gifticon.domain.command;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GifticonRegisterCommand {
  private final String name;
  private final String description;
  private final String giftCode;
  private final Long originalPrice;
  private final Long price;
  private final LocalDate expirationDate;

  public GifticonRegisterCommand(String name, String description, String giftCode, Long originalPrice, Long price, LocalDate expirationDate) {
    this.name = name;
    this.description = description;
    this.giftCode = giftCode;
    this.originalPrice = originalPrice;
    this.price = price;
    this.expirationDate = expirationDate;
  }
}
