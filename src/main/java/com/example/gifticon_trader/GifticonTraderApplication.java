package com.example.gifticon_trader;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@OpenAPIDefinition(info = @Info(title = "Gifticon Trader API", version = "0.0",
        description = "gifticon trader service"))
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRedisIndexedHttpSession
public class GifticonTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(GifticonTraderApplication.class, args);
  }

}
