package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.event.UserRegisteredEvent;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserEventHandler {
  private final UserRepository userRepository;
  private final NotificationService notificationService;

  @TransactionalEventListener
  public void handleUserRegisteredEvent(UserRegisteredEvent event) {
    User existUser = this.userRepository.findByUsername(event.email()).orElseThrow(
            () -> new IllegalArgumentException("User not found")
    );
    this.notificationService.createWelcomeNotification(existUser.getId());

  }
}
