package com.example.gifticon_trader.unit.gifticon.domain;

import com.example.gifticon_trader.gifticon.domain.GiftCode;
import com.example.gifticon_trader.gifticon.domain.exception.InvalidGiftCodeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GiftCodeTest {

  @Test
  void test_GiftCodeCreation() {
    // given
    String giftCode = "123456789";

    // when
    GiftCode code = GiftCode.of(giftCode);

    // then
    assertNotNull(code);
    assertEquals(giftCode, code.getCode());
  }

  @Test
  void test_GiftCodeCreation_shouldThrowException_when_codeIsNull() {
    // given
    String giftCode1 = null;
    String giftCode2 = "1234567";

    // when & then
    assertThatThrownBy(() -> GiftCode.of(giftCode1))
            .isInstanceOf(InvalidGiftCodeException.class);
    assertThatThrownBy(() -> GiftCode.of(giftCode2))
            .isInstanceOf(InvalidGiftCodeException.class);
  }

}