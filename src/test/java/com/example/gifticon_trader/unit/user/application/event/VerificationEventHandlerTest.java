package com.example.gifticon_trader.unit.user.application.event;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.user.application.event.VerificationEventHandler;
import com.example.gifticon_trader.user.domain.event.VerificationEmailEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VerificationEventHandlerTest {
  @Mock
  NotificationService notificationService;

  @InjectMocks
  VerificationEventHandler verificationEventHandler;

  @Test
  void handleVerificationEmailEvent() {
    // given
    Long userId = 1L;
    Long referenceId = 1L;

    // when
    verificationEventHandler.handleVerificationEmailEvent(new VerificationEmailEvent(userId, referenceId));

    // then
    verify(notificationService).createEmailVerificationNotification(userId, referenceId);
  }
}