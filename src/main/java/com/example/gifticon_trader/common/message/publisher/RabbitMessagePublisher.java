package com.example.gifticon_trader.common.message.publisher;

import org.springframework.amqp.core.AmqpTemplate;

public class RabbitMessagePublisher implements MessagePublisher {
  private final AmqpTemplate amqpTemplate;

  public RabbitMessagePublisher(AmqpTemplate amqpTemplate) {
    this.amqpTemplate = amqpTemplate;
  }

  @Override
  public void publish(String topic, Object message) {
    this.amqpTemplate.convertAndSend(topic, message);
  }
}
