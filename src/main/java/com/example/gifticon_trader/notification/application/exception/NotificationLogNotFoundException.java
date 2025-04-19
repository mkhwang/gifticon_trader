package com.example.gifticon_trader.notification.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class NotificationLogNotFoundException extends BizException {
  public NotificationLogNotFoundException() {
    super("notification.log.not.found");
  }
}
