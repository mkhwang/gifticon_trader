package com.example.gifticon_trader.user.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class TokenNotFoundException extends BizException {

  public TokenNotFoundException() {
    super("verification.token.not.found");
  }
}
