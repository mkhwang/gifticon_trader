package com.example.gifticon_trader.unit.user.application.event;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.user.application.event.VerificationEventHandler;
import com.example.gifticon_trader.user.domain.event.VerificationEmailEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    notificationService = mock(NotificationService.class);
    verificationEventHandler = new VerificationEventHandler(notificationService);

    // when
    verificationEventHandler.handleVerificationEmailEvent(new VerificationEmailEvent(userId, referenceId));

    // then
    verify(notificationService).createEmailVerificationNotification(userId, referenceId);
  }
}