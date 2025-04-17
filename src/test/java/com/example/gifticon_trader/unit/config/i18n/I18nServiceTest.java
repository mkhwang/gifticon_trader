package com.example.gifticon_trader.unit.config.i18n;

import com.example.gifticon_trader.config.i18n.I18nService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class I18nServiceTest {

  @Mock
  MessageSource messageSource;

  @InjectMocks
  I18nService i18nService;

  @Test
  void getMessage_shouldDelegateToMessageSource() {
    // given
    given(messageSource.getMessage(eq("hello"), any(), any()))
            .willReturn("world");

    // when
    String result = i18nService.getMessage("hello");

    // then
    assertThat(result).isEqualTo("world");
  }
}