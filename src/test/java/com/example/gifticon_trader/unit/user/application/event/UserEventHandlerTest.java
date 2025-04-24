package com.example.gifticon_trader.unit.user.application.event;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.user.application.event.UserEventHandler;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.event.UserRegisteredEvent;
import com.example.gifticon_trader.user.infra.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventHandlerTest {
  @Mock
  UserRepository userRepository;

  @Mock
  NotificationService notificationService;

  @InjectMocks
  UserEventHandler userEventHandler;

  @Test
  void handleUserRegisteredEvent_shouldSendVerificationEmail() {
    // given
    String email = "test@example.com";
    UserRegisteredEvent event = new UserRegisteredEvent(email);
    User user = mock(User.class);

    given(user.getId()).willReturn(1L);
    given(userRepository.findByUsername(email)).willReturn(Optional.of(user));

    // when
    userEventHandler.handleUserRegisteredEvent(event);

    // then
    verify(notificationService).createWelcomeNotification(user.getId());

  }
}