package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotCancelTradeException extends BizException {

  public CanNotCancelTradeException() {
    super("gifticon.can.not.cancel.trade");
  }
}