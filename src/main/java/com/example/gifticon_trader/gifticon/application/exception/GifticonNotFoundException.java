package com.example.gifticon_trader.gifticon.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class GifticonNotFoundException extends BizException {

  public GifticonNotFoundException() {
    super("gifticon.not.found");
  }
}