package com.example.gifticon_trader.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationTokenDto {
  @NotEmpty
  private String token;
}
