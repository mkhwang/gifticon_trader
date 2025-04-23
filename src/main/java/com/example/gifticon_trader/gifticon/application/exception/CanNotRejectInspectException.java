package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotRejectInspectException extends BizException {

  public CanNotRejectInspectException() {
    super("gifticon.can.not.reject.inspect");
  }
}