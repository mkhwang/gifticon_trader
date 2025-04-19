package com.example.gifticon_trader.unit.user.application;

import com.example.gifticon_trader.common.email.EmailService;
import com.example.gifticon_trader.user.application.UserEventHandler;
import com.example.gifticon_trader.user.domain.event.UserRegisteredEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventHandlerTest {

  @Mock
  EmailService emailService;

  @InjectMocks
  UserEventHandler userEventHandler;

  @Test
  void handleUserRegisteredEvent_shouldSendVerificationEmail() {
    // given
    String email = "test@example.com";
    UserRegisteredEvent event = new UserRegisteredEvent(email);

    // when
    userEventHandler.handleUserRegisteredEvent(event);

    // then
    verify(emailService).sendEmail(
        eq(email),
        eq("Welcome to Gifticon Trader!"),
        eq("Thank you for registering with us!")
    );

  }
}