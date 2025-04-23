package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotRequestInspectException extends BizException {

  public CanNotRequestInspectException() {
    super("gifticon.can.not.reqest.inspect");
  }
}