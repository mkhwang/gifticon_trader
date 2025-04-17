package com.example.gifticon_trader.unit.config.exception;

import com.example.gifticon_trader.config.exception.BizException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;


class BizExceptionTest {

  @Test
  void constructor_setsAllFieldsCorrectly() {
    BizException ex = new BizException(
            HttpStatus.NOT_FOUND,
            "user.not.found",
            new Throwable("inner"),
            new Object[]{"userId"}
    );

    assertThat(ex.getCode()).isEqualTo("user.not.found");
    assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(ex.getArgs()).containsExactly("userId");
    assertThat(ex.getCause().getMessage()).isEqualTo("inner");
  }

  @Test
  void constructor_defaultsToBadRequest() {
    BizException ex = new BizException("invalid.input");

    assertThat(ex.getCode()).isEqualTo("invalid.input");
    assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(ex.getArgs()).isNull();
  }

}