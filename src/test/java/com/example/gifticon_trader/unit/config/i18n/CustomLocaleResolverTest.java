package com.example.gifticon_trader.unit.config.i18n;

import com.example.gifticon_trader.config.i18n.CustomLocaleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomLocaleResolverTest {
  private final CustomLocaleResolver resolver = new CustomLocaleResolver();

  @BeforeEach
  void setUp() {
    resolver.setSupportedLocales(List.of(
            new Locale("ko", "KR"),
            new Locale("en", "US")
    ));
  }

  @Test
  void shouldReturnDefaultLocale_whenNoHeader() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.removeHeader("Accept-Language");

    Locale result = resolver.resolveLocale(request);

    assertThat(result).isEqualTo(Locale.KOREA);
  }

  @Test
  void shouldReturnMappedLocale_whenOnlyLanguageProvided() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Accept-Language", "ko");

    Locale result = resolver.resolveLocale(request);

    assertThat(result).isEqualTo(Locale.KOREA);
  }

  @Test
  void shouldReturnExactMatch_whenFullLocaleProvidedAndSupported() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Accept-Language", "en-US");

    Locale result = resolver.resolveLocale(request);

    assertThat(result).isEqualTo(Locale.US);
  }

  @Test
  void shouldFallbackToDefault_whenUnsupportedLanguageProvided() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Accept-Language", "fr");

    Locale result = resolver.resolveLocale(request);

    assertThat(result).isEqualTo(Locale.KOREA); // fallback
  }
}