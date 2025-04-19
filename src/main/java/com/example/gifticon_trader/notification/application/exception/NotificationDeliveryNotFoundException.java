package com.example.gifticon_trader.notification.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class NotificationDeliveryNotFoundException extends BizException {
  public NotificationDeliveryNotFoundException() {
    super("notification.delivery.not.found");
  }
}
