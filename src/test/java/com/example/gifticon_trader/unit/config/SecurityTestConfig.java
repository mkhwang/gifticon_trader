package com.example.gifticon_trader.unit.config;

import com.example.gifticon_trader.config.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
class SecurityTestConfig {
}
