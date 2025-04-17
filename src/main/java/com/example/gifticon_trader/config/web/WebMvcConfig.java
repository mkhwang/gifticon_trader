package com.example.gifticon_trader.config.web;

import com.example.gifticon_trader.config.i18n.CustomLocaleResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver resolver = new CustomLocaleResolver();
    // 지원할 Locale 리스트 설정
    resolver.setSupportedLocales(List.of(
      new Locale("ko", "KR"),
      new Locale("en", "US")
    ));

    // Default locale도 설정
    resolver.setDefaultLocale(new Locale("ko", "KR"));

    return resolver;
  }
}
