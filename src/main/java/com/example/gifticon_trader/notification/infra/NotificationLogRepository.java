package com.example.gifticon_trader.notification.infra;

import com.example.gifticon_trader.notification.domain.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}
