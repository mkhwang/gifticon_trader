package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class InvalidPriceException extends BizException {

  public InvalidPriceException() {
    super("gifticon.price.invalid");
  }
}