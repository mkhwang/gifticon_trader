package com.example.gifticon_trader.notification.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class NotSupportedNotificationType extends BizException {
  public NotSupportedNotificationType() {
    super("notification.type.not.supported");
  }
}
