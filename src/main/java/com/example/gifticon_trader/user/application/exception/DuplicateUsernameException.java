package com.example.gifticon_trader.user.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class DuplicateUsernameException extends BizException {

  public DuplicateUsernameException() {
    super("signup.form.email.duplicated");
  }
}