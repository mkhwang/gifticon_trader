package com.example.gifticon_trader.notification.infra;

import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDeliveryLogRepository extends JpaRepository<NotificationDeliveryLog, Long> {
}
