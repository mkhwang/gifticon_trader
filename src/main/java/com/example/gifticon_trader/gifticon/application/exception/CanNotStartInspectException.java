package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotStartInspectException extends BizException {

  public CanNotStartInspectException() {
    super("gifticon.can.not.start.inspect");
  }
}