package com.example.gifticon_trader.unit.config.security;

import com.example.gifticon_trader.config.security.SecurityProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityPropertiesTest.TestConfig.class)
@TestPropertySource(
        properties = {"app.security.public-urls=/login,/swagger-ui/**"}
)
class SecurityPropertiesTest {

   @EnableConfigurationProperties(SecurityProperties.class)
   static class TestConfig {}

   @Autowired
   private SecurityProperties securityProperties;

   @Test
   void testPublicUrls() {
      assertArrayEquals(new String[]{"/login", "/swagger-ui/**"}, securityProperties.getPublicUrls());
   }
}