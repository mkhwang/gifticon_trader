package com.example.gifticon_trader.unit.common.message.publisher;

import com.example.gifticon_trader.common.message.publisher.RabbitMessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMessagePublisherTest {

  @Mock
  AmqpTemplate amqpTemplate;

  RabbitMessagePublisher rabbitMessagePublisher;

  @BeforeEach
  void beforeEach() {
    rabbitMessagePublisher = new RabbitMessagePublisher(amqpTemplate);
  }

  @Test
  void testPublish() {
    // Given
    String topic = "hello";
    String message = "world!";

    // When
    rabbitMessagePublisher.publish(topic, message);

    // Then
    verify(amqpTemplate).convertAndSend(topic, message);
  }
}