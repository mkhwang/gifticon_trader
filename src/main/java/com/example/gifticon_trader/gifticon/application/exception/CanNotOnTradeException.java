package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotOnTradeException extends BizException {

  public CanNotOnTradeException() {
    super("gifticon.can.not.change.on.trade");
  }
}