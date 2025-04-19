package com.example.gifticon_trader.common.message.publisher;

public interface MessagePublisher {

  void publish(String topic, Object message);
}
