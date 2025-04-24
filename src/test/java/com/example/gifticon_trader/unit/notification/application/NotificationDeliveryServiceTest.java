package com.example.gifticon_trader.unit.notification.application;

import com.example.gifticon_trader.notification.application.NotificationDeliveryService;
import com.example.gifticon_trader.notification.application.handler.NotificationHandler;
import com.example.gifticon_trader.notification.application.handler.NotificationHandlerFactory;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
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
class NotificationDeliveryServiceTest {
  @Mock
  NotificationHandlerFactory notificationHandlerFactory;

  @Mock
  NotificationDeliveryLogRepository notificationDeliveryLogRepository;

  @InjectMocks
  NotificationDeliveryService notificationDeliveryService;

  @Test
  void process() {
    // Given
    Long deliveryId = 1L;
    NotificationType notificationType = NotificationType.WELCOME;

    NotificationLog mockNotificationLog = mock(NotificationLog.class);
    NotificationDeliveryLog mockDeliveryLog = mock(NotificationDeliveryLog.class);
    NotificationHandler mockNotificationHandler = mock(NotificationHandler.class);

    given(mockDeliveryLog.getId()).willReturn(1L);
    given(mockNotificationLog.getNotificationType()).willReturn(notificationType);
    given(mockDeliveryLog.getNotificationLog()).willReturn(mockNotificationLog);
    given(notificationDeliveryLogRepository.findById(deliveryId)).willReturn(Optional.of(mockDeliveryLog));
    given(notificationHandlerFactory.getHandler(notificationType)).willReturn(mockNotificationHandler);

    // When
    notificationDeliveryService.process(deliveryId);

    // Then
    verify(notificationDeliveryLogRepository).findById(deliveryId);
    verify(notificationHandlerFactory).getHandler(notificationType);
    verify(mockNotificationHandler).send(deliveryId);
    verify(mockDeliveryLog).markSent();
  }
}