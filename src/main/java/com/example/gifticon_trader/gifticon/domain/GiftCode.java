package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.gifticon.domain.exception.InvalidGiftCodeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class GiftCode {
  @Column(name = "gift_code", nullable = false)
  private String code;

  private GiftCode(String code) {
    this.code = code;
  }

  public GiftCode() {
  }

  public static GiftCode of(String code) {
    if (code == null || code.length() < 8) {
      throw new InvalidGiftCodeException();
    }
    return new GiftCode(code);
  }
}
