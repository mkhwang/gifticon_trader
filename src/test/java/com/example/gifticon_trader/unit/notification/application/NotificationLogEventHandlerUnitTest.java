package com.example.gifticon_trader.unit.notification.application;

import com.example.gifticon_trader.common.message.publisher.MessagePublisher;
import com.example.gifticon_trader.config.mesage.MessageConstants;
import com.example.gifticon_trader.notification.application.NotificationLogEventHandler;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.application.resolver.payload.assembler.NotificationPayloadAssembler;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.event.NotificationCreatedEvent;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import com.example.gifticon_trader.notification.infra.NotificationLogRepository;
import com.example.gifticon_trader.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationLogEventHandlerUnitTest {

  @Mock
  NotificationLogEventHandler handler;

  @Mock
  NotificationLogRepository notificationLogRepository;

  @Mock
  NotificationDeliveryLogRepository deliveryLogRepository;

  @Mock
  MessagePublisher messagePublisher;

  @Mock
  NotificationLogResolver logResolver;

  @Mock
  NotificationPayloadAssembler assembler;

  @Test
  void handleNotificationLogEvent_shouldPublishDelivery() {
    // given
    Long logId = 1L;
    NotificationType type = NotificationType.WELCOME;
    NotificationChannel channel = NotificationChannel.EMAIL;

    User mockUser = mock(User.class);
    NotificationLog log = mock(NotificationLog.class);
    given(log.getNotificationType()).willReturn(type);
    given(log.getReferenceId()).willReturn(null);
    given(log.getUser()).willReturn(mockUser);
    given(log.getNotificationChannels()).willReturn(Set.of(channel));
    given(notificationLogRepository.findById(logId)).willReturn(Optional.of(log));

    NotificationLogRecord record = new NotificationLogRecord("title", "msg", type, null);
    given(logResolver.supports(type)).willReturn(true);
    given(logResolver.resolve(mockUser, type, null)).willReturn(record);

    NotificationPayload payload = mock(NotificationPayload.class);
    given(assembler.supports(channel)).willReturn(true);
    given(assembler.assemble(record)).willReturn(payload);

    handler = new NotificationLogEventHandler(
            notificationLogRepository,
            deliveryLogRepository,
            List.of(logResolver),
            List.of(assembler),
            messagePublisher
    );

    // when
    handler.handleNotificationLogEvent(new NotificationCreatedEvent(logId));

    // then
    verify(deliveryLogRepository).save(any(NotificationDeliveryLog.class));
    verify(messagePublisher).publish(eq(MessageConstants.NOTIFICATION_TOPIC), any());
  }
}