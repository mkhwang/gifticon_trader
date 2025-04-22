package com.example.gifticon_trader.unit.common.domain;

import com.example.gifticon_trader.common.domain.Money;
import com.example.gifticon_trader.common.exception.InvalidMoneyArgumentException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

  @Test
  void testMoneyCreation() {
    // given
    long amount = 1000L;

    // when
    Money money = Money.of(amount);

    // then
    assertEquals(amount, money.getAmount());
  }

  @Test
  void test_money_shouldThrowException_when_minusValue() {
    // given
    long amount = -1L;

    // when & then
    assertThatThrownBy(() -> Money.of(amount))
            .isInstanceOf(InvalidMoneyArgumentException.class);
  }
}