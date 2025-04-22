package com.example.gifticon_trader.gifticon.domain.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class InvalidGiftCodeException extends BizException {
  public InvalidGiftCodeException() {
    super("invalid.gift.code");
  }
}
