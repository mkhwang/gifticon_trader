package com.example.gifticon_trader.unit.config.i18n;

import com.example.gifticon_trader.config.i18n.Lang;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LangTest {
  @Test
  void from_shouldReturnEnum_whenValidCode() {
    assertThat(Lang.from("ko-kr")).isEqualTo(Lang.KO_KR);
    assertThat(Lang.from("EN-US")).isEqualTo(Lang.EN_US);
  }

  @Test
  void from_shouldThrowException_whenInvalidCode() {
    assertThatThrownBy(() -> Lang.from("jp"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown language code");
  }

  @Test
  void fromJson_shouldReturnEnum_whenValidCode() {
    assertThat(Lang.fromJson("en-us")).isEqualTo(Lang.EN_US);
  }

  @Test
  void getLocale_shouldReturnExpectedLocale() {
    assertThat(Lang.KO_KR.getLocale()).isEqualTo(Locale.KOREAN);
    assertThat(Lang.EN_US.getLocale()).isEqualTo(Locale.US);
  }

  @Test
  void toString_shouldReturnCode() {
    assertThat(Lang.KO_KR.toString()).isEqualTo("ko-kr");
  }
}