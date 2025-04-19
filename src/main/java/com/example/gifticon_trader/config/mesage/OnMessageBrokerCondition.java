package com.example.gifticon_trader.config.mesage;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class OnMessageBrokerCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String configuredValue = context.getEnvironment()
            .getProperty("app.message.broker", "RABBIT");

    if (!metadata.isAnnotated(ConditionalOnMessageBroker.class.getName())) return false;

    Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnMessageBroker.class.getName());
    MessageBrokerType expected = (MessageBrokerType) attrs.get("value");

    return expected.name().equalsIgnoreCase(configuredValue);
  }
}