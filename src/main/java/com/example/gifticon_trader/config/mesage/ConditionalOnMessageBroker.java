package com.example.gifticon_trader.config.mesage;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnMessageBrokerCondition.class)
public @interface ConditionalOnMessageBroker {
  MessageBrokerType value();
}