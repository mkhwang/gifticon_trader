package com.example.gifticon_trader.testsupport.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@TestConfiguration
public class TestMessageSourceConfig {
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("classpath:/messages");
    ms.setDefaultEncoding("UTF-8");
    return ms;
  }
}
