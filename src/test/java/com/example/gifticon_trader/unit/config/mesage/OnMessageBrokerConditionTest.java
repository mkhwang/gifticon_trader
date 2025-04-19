package com.example.gifticon_trader.unit.config.mesage;

import com.example.gifticon_trader.config.mesage.MessageBrokerType;
import com.example.gifticon_trader.config.mesage.OnMessageBrokerCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OnMessageBrokerConditionTest {

  @Mock
  ConditionContext context;

  @Mock
  AnnotatedTypeMetadata metadata;

  @Mock
  Environment mockEnv;

  OnMessageBrokerCondition condition = new OnMessageBrokerCondition();

  @Test
  void shouldMatchWhenBrokerMatchesEnum() {
    // given
    given(context.getEnvironment()).willReturn(mockEnv);
    given(mockEnv.getProperty("app.message.broker", "RABBIT")).willReturn("KAFKA");

    Map<String, Object> attrs = Map.of("value", MessageBrokerType.KAFKA);
    given(metadata.isAnnotated(anyString())).willReturn(true);
    given(metadata.getAnnotationAttributes(anyString())).willReturn(attrs);

    // when
    boolean result = condition.matches(context, metadata);

    // then
    assertThat(result).isTrue();
  }

  @Test
  void shouldNotMatchWhenBrokerDoesNotMatchEnum() {
    // given
    Environment mockEnv = mock(Environment.class);
    given(context.getEnvironment()).willReturn(mockEnv);
    given(mockEnv.getProperty("app.message.broker", "RABBIT")).willReturn("RABBIT");

    Map<String, Object> attrs = Map.of("value", MessageBrokerType.KAFKA);
    given(metadata.isAnnotated(anyString())).willReturn(true);
    given(metadata.getAnnotationAttributes(anyString())).willReturn(attrs);

    // when
    boolean result = condition.matches(context, metadata);

    // then
    assertThat(result).isFalse();
  }

  @Test
  void shouldNotMatchWhenAnnotationMissing() {
    // given
    given(context.getEnvironment()).willReturn(mockEnv);
    given(metadata.isAnnotated(anyString())).willReturn(false);

    // when
    boolean result = condition.matches(context, metadata);

    // then
    assertThat(result).isFalse();
  }
}