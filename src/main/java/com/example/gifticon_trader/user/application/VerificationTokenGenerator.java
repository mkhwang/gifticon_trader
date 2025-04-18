package com.example.gifticon_trader.user.application;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerificationTokenGenerator {
  public String generate() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
}
