package com.example.gifticon_trader.user.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class InvalidTokenException extends BizException {

  public InvalidTokenException() {
    super("signup.form.verification-token.invalid");
  }
}
