package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class InvalidExpirationDateException extends BizException {

  public InvalidExpirationDateException() {
    super("gifticon.expirationDate.invalid");
  }
}