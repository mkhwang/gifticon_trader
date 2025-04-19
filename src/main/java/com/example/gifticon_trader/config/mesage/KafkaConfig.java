package com.example.gifticon_trader.config.mesage;

import com.example.gifticon_trader.common.message.KafkaMessagePublisher;
import com.example.gifticon_trader.common.message.MessagePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnMessageBroker(MessageBrokerType.KAFKA)
public class KafkaConfig {
  @Bean
  public MessagePublisher kafkaMessagePublisher(KafkaTemplate<String, Object> kafkaTemplate) {
    return new KafkaMessagePublisher(kafkaTemplate);
  }
}
