package com.example.gifticon_trader.config.mesage;

import com.example.gifticon_trader.common.message.publisher.MessagePublisher;
import com.example.gifticon_trader.common.message.publisher.RabbitMessagePublisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMessageBroker(MessageBrokerType.RABBIT)
public class RabbitMqConfig {

  @Bean
  public MessagePublisher rabbitMessagePublisher(AmqpTemplate amqpTemplate) {
    return new RabbitMessagePublisher(amqpTemplate);
  }

  @Bean
  public Queue notificationQueue() {
    return new Queue(MessageConstants.NOTIFICATION_TOPIC, true);
  }
}
