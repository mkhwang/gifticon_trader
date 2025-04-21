package com.example.gifticon_trader.notification.application.resolver.payload.assembler;

import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;

public interface NotificationPayloadAssembler {

  boolean supports(NotificationChannel channel);

  NotificationPayload assemble(NotificationLogRecord message);
}
