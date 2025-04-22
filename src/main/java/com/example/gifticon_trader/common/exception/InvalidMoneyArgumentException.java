package com.example.gifticon_trader.common.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class InvalidMoneyArgumentException extends BizException {

  public InvalidMoneyArgumentException() {
    super("invalid.money.argument");
  }
}