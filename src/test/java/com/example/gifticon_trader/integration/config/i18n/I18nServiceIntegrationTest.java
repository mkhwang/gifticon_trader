package com.example.gifticon_trader.integration.config.i18n;

import com.example.gifticon_trader.config.i18n.I18nService;
import com.example.gifticon_trader.testsupport.config.TestMessageSourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(TestMessageSourceConfig.class)
@ContextConfiguration(classes = {I18nService.class})
class I18nServiceIntegrationTest {


  @Autowired
  private I18nService i18nService;


  @Test
  void getMessage_shouldReturnKoreanMessage() {
    // given
    LocaleContextHolder.setLocale(Locale.KOREA);

    // when
    String message = i18nService.getMessage("hello");

    // then
    assertThat(message).isEqualTo("world");
  }
}