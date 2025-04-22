package com.example.gifticon_trader.notification.application;

import com.example.gifticon_trader.notification.application.exception.NotSupportedNotificationType;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationLogResolver;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.event.NotificationCreatedEvent;
import com.example.gifticon_trader.notification.infra.NotificationLogRepository;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
  private final UserRepository userRepository;
  private final NotificationLogRepository notificationLogRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final List<NotificationLogResolver> notificationLogResolvers;

  @Transactional
  public void createWelcomeNotification(Long userId) {
    User user = userRepository.findById(userId).orElseThrow();

    NotificationLogRecord message = notificationLogResolvers.stream()
            .filter(r -> r.supports(NotificationType.WELCOME))
            .findFirst()
            .orElseThrow(NotSupportedNotificationType::new)
            .resolve(user, NotificationType.WELCOME, null);


    NotificationLog log = NotificationLog.create(user, NotificationType.WELCOME, message.title(), message.message());
    notificationLogRepository.save(log);

    eventPublisher.publishEvent(new NotificationCreatedEvent(log.getId()));
  }

  @Transactional
  public void createEmailVerificationNotification(Long userId, Long referenceId) {
    User user = userRepository.findById(userId).orElseThrow();

    NotificationLogRecord message = notificationLogResolvers.stream()
            .filter(r -> r.supports(NotificationType.VERIFY_EMAIL))
            .findFirst()
            .orElseThrow(NotSupportedNotificationType::new)
            .resolve(user, NotificationType.VERIFY_EMAIL, referenceId);

    NotificationLog log = NotificationLog.createWithReference(user, NotificationType.VERIFY_EMAIL, message.title(), message.message(), referenceId);
    notificationLogRepository.save(log);

    eventPublisher.publishEvent(new NotificationCreatedEvent(log.getId()));
  }

}
