package com.example.gifticon_trader.user.application.event;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.user.domain.event.VerificationEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class VerificationEventHandler {

  private final NotificationService notificationService;;

  @TransactionalEventListener
  public void handleVerificationEmailEvent(VerificationEmailEvent event) {
    notificationService.createEmailVerificationNotification(event.userId(), event.verificationId());

  }
}
