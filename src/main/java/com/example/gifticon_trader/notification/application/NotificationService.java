package com.example.gifticon_trader.notification.application;

import com.example.gifticon_trader.notification.application.exception.NotSupportedNotificationType;
import com.example.gifticon_trader.notification.application.resolver.message.NotificationMessageResolver;
import com.example.gifticon_trader.notification.domain.NotificationLog;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationMessage;
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
  private final List<NotificationMessageResolver> notificationMessageResolvers;

  @Transactional
  public void createWelcomeNotification(Long userId) {
    User user = userRepository.findById(userId).orElseThrow();

    NotificationMessage message = notificationMessageResolvers.stream()
            .filter(r -> r.supports(NotificationType.WELCOME))
            .findFirst()
            .orElseThrow(NotSupportedNotificationType::new)
            .resolve(user, NotificationType.WELCOME);


    NotificationLog log = NotificationLog.create(user, NotificationType.WELCOME, message.title(), message.message());
    notificationLogRepository.save(log);

    eventPublisher.publishEvent(new NotificationCreatedEvent(log.getId()));
  }

}
