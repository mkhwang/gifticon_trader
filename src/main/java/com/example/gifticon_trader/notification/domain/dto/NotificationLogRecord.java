package com.example.gifticon_trader.notification.domain.dto;

import com.example.gifticon_trader.notification.domain.NotificationType;

public record NotificationLogRecord(String title, String message, NotificationType notificationType, Long referenceId) { }
