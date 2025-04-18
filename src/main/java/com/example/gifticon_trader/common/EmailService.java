package com.example.gifticon_trader.common;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;

  public void sendEmail(String to, String subject, String text) {

  }
}
