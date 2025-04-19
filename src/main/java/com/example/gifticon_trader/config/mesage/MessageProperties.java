package com.example.gifticon_trader.config.mesage;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app.message")
public class MessageProperties {
  private MessageBrokerType brokerType;
}
