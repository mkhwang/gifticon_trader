package com.example.gifticon_trader.common.message;

public interface MessagePublisher {

  void publish(String topic, Object message);
}
