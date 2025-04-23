package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotCompleteTradeException extends BizException {

  public CanNotCompleteTradeException() {
    super("gifticon.can.not.complete.trade");
  }
}