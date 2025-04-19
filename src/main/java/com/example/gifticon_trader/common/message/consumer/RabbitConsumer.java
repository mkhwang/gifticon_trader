package com.example.gifticon_trader.common.message.consumer;

import com.example.gifticon_trader.config.mesage.ConditionalOnMessageBroker;
import com.example.gifticon_trader.config.mesage.MessageBrokerType;
import com.example.gifticon_trader.config.mesage.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnMessageBroker(MessageBrokerType.RABBIT)
@Component
@RequiredArgsConstructor
public class RabbitConsumer {

  @RabbitListener(queues = MessageConstants.NOTIFICATION_TOPIC)
  public void consumeJob(Object message) {
    log.debug(message.toString());
  }
}
