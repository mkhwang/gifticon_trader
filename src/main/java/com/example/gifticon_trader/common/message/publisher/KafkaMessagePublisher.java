package com.example.gifticon_trader.common.message.publisher;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessagePublisher implements MessagePublisher {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public KafkaMessagePublisher(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void publish(String topic, Object message) {
    kafkaTemplate.send(topic, message);
  }
}
