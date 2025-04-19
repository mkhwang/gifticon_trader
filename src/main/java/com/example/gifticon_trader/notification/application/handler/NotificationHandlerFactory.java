package com.example.gifticon_trader.notification.application.handler;

import com.example.gifticon_trader.notification.application.exception.NotificationDeliveryHandlerNotFoundException;
import com.example.gifticon_trader.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationHandlerFactory {

  private final List<NotificationHandler> handlers;

  public NotificationHandler getHandler(NotificationType notificationType) {
    return handlers.stream()
            .filter(h -> h.supports(notificationType))
            .findFirst()
            .orElseThrow(NotificationDeliveryHandlerNotFoundException::new);
  }
}