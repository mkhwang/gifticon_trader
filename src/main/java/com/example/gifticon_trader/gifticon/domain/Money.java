package com.example.gifticon_trader.gifticon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Money {
  @Column(name = "price", nullable = false)
  private Long amount;

  private Money(Long amount) {
    this.amount = amount;
  }

  public Money() {
    this.amount = 0L;
  }

  public static Money of(Long amount) {
    return new Money(amount);
  }
}
