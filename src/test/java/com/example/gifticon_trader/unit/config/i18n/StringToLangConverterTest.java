package com.example.gifticon_trader.unit.config.i18n;

import com.example.gifticon_trader.config.i18n.Lang;
import com.example.gifticon_trader.config.i18n.StringToLangConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class StringToLangConverterTest {

  private final StringToLangConverter converter = new StringToLangConverter();

  @Test
  void convert_shouldReturnValidLang_whenInputIsValid() {
    // given
    String source = "ko-kr";

    // when
    Lang result = converter.convert(source);

    // then
    assertThat(result).isEqualTo(Lang.KO_KR);
  }

  @Test
  void convert_shouldThrowException_whenInputIsInvalid() {
    // given
    String source = "zz";

    // when / then
    assertThatThrownBy(() -> converter.convert(source))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown language code");
  }
}