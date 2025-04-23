package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class CanNotGifticonChangeCodeException extends BizException {

  public CanNotGifticonChangeCodeException() {
    super("gifticon.can.not.change.code");
  }
}