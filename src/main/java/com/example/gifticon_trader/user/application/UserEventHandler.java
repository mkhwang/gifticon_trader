package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.common.EmailService;
import com.example.gifticon_trader.user.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {
  private final EmailService emailService;

  @EventListener
  public void handleUserRegisteredEvent(UserRegisteredEvent event) {
    String email = event.email();
    String subject = "Welcome to Gifticon Trader!";
    String text = "Thank you for registering with us!";

    emailService.sendEmail(email, subject, text);
  }
}
