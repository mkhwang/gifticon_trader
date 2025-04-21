package com.example.gifticon_trader.unit.common.domain;

import com.example.gifticon_trader.common.domain.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

  @Test
  void testMoneyCreation() {
    Money money = Money.of(1000L);
    assertEquals(1000, money.getAmount());
  }

}