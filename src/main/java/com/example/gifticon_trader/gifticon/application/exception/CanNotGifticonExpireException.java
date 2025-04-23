package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotGifticonExpireException extends BizException {

  public CanNotGifticonExpireException() {
    super("gifticon.can.not.expire");
  }
}