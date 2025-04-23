package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotGifticonSettleException extends BizException {

  public CanNotGifticonSettleException() {
    super("gifticon.can.not.settle");
  }
}