package com.example.gifticon_trader.notification.application.resolver.payload.assembler;

import com.example.gifticon_trader.notification.application.exception.NotSupportedNotificationType;
import com.example.gifticon_trader.notification.application.resolver.payload.email.EmailNotificationPayloadResolver;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailNotificationPayloadAssembler implements NotificationPayloadAssembler {
  private final List<EmailNotificationPayloadResolver> emailNotificationPayloadResolvers;

  @Override
  public boolean supports(NotificationChannel channel) {
    return channel == NotificationChannel.EMAIL;
  }

  @Override
  public NotificationPayload assemble(NotificationLogRecord message) {
    EmailNotificationPayloadResolver foundResolver = emailNotificationPayloadResolvers.stream()
            .filter(resolver -> resolver.supports(message.notificationType()))
            .findFirst()
            .orElseThrow(NotSupportedNotificationType::new);
    return foundResolver.resolve(message);
  }
}