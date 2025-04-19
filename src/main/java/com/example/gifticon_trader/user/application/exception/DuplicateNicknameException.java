package com.example.gifticon_trader.user.application.exception;

import com.example.gifticon_trader.config.exception.BizException;

public class DuplicateNicknameException extends BizException {

  public DuplicateNicknameException() {
    super("signup.form.nickname.duplicated");
  }
}