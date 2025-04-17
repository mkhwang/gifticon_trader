package com.example.gifticon_trader.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
@Getter
@Setter
public class SecurityProperties {
  @Value("${app.security.public-urls:/actuator/**, /swagger-ui/**, /v3/api-docs/**, /register, /forgot-password, /password-reset, /password-reset/**, /verify-email, /verify-email-complete, /webapp/**}")
  private String[] publicUrls;
}
