package com.example.gifticon_trader.unit.notification.application;

import com.example.gifticon_trader.notification.application.NotificationService;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.event.NotificationCreatedEvent;
import com.example.gifticon_trader.notification.infra.NotificationLogRepository;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceUnitTest {
  @Mock
  UserRepository userRepository;

  @Mock
  NotificationLogRepository notificationLogRepository;

  @Mock
  ApplicationEventPublisher eventPublisher;

  @Mock
  NotificationLogResolver welcomeResolver;

  @InjectMocks
  NotificationService notificationService;

  @Test
  void createWelcomeNotification_shouldCreateLogAndPublishEvent() {
    // given
    Long userId = 1L;
    User mockUser = mock(User.class);
    given(userRepository.findById(userId)).willReturn(Optional.of(mockUser));

    NotificationLogRecord record = new NotificationLogRecord("Welcome Title", "Welcome Message", NotificationType.WELCOME, null);
    given(welcomeResolver.supports(NotificationType.WELCOME)).willReturn(true);
    given(welcomeResolver.resolve(mockUser, NotificationType.WELCOME, null)).willReturn(record);

    notificationService = new NotificationService(
            userRepository,
            notificationLogRepository,
            eventPublisher,
            List.of(welcomeResolver)
    );

    // when
    notificationService.createWelcomeNotification(userId);

    // then
    verify(userRepository).findById(userId);
    verify(welcomeResolver).supports(NotificationType.WELCOME);
    verify(welcomeResolver).resolve(mockUser, NotificationType.WELCOME, null);
    verify(notificationLogRepository).save(any(NotificationLog.class));
    verify(eventPublisher).publishEvent(any(NotificationCreatedEvent.class));
  }

}