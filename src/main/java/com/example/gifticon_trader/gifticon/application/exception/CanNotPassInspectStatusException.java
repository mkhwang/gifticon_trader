package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotPassInspectStatusException extends BizException {

  public CanNotPassInspectStatusException() {
    super("gifticon.can.not.pass.inspect");
  }
}