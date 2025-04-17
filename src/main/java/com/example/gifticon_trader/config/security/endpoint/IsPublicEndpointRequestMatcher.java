package com.example.gifticon_trader.config.security.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

@RequiredArgsConstructor
@Component
public class IsPublicEndpointRequestMatcher implements RequestMatcher {

  private final List<HandlerMapping> handlerMappings;

  @Override
  public boolean matches(HttpServletRequest request) {
    try {
      for (HandlerMapping mapping : handlerMappings) {
        HandlerExecutionChain handler = mapping.getHandler(request);
        if (handler != null && handler.getHandler() instanceof HandlerMethod handlerMethod) {
          return handlerMethod.getMethod().isAnnotationPresent(IsPublicEndpoint.class)
              || handlerMethod.getBeanType().isAnnotationPresent(IsPublicEndpoint.class);
        }
      }
    } catch (Exception ignored) {
    }

    return false;
  }
}
