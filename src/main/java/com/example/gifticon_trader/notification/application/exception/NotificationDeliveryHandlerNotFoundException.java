package com.example.gifticon_trader.notification.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class NotificationDeliveryHandlerNotFoundException extends BizException {
  public NotificationDeliveryHandlerNotFoundException() {
    super("notification.delivery.handler.not.found");
  }
}
