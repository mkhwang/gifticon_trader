package com.example.gifticon_trader.unit.config.security.endpoint;

import com.example.gifticon_trader.config.security.endpoint.IsPublicEndpoint;
import com.example.gifticon_trader.config.security.endpoint.IsPublicEndpointRequestMatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IsPublicEndpointRequestMatcherTest {
  @Mock
  HttpServletRequest mockRequest;

  @Test
  void shouldReturnTrue_whenHandlerHasAnnotation() throws Exception {
    // given
    HandlerMethod handlerMethod = new HandlerMethod(
            new AnnotatedController(), AnnotatedController.class.getMethod("publicEndPoint")
    );

    HandlerExecutionChain chain = new HandlerExecutionChain(handlerMethod);

    HandlerMapping mapping = mock(HandlerMapping.class);
    when(mapping.getHandler(mockRequest)).thenReturn(chain);

    IsPublicEndpointRequestMatcher matcher = new IsPublicEndpointRequestMatcher(List.of(mapping));

    // when
    boolean result = matcher.matches(mockRequest);

    // then
    assertThat(result).isTrue();
  }

  static class AnnotatedController {
    @IsPublicEndpoint
    public void publicEndPoint() {
    }
  }
}