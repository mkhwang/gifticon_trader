package com.example.gifticon_trader.notification.domain;

import com.example.gifticon_trader.config.security.audit.AuditBaseEntity;
import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Entity
public class NotificationDeliveryLog extends AuditBaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private NotificationLog notificationLog;

  @Enumerated(EnumType.STRING)
  private NotificationChannel channel;

  private LocalDateTime sentAt;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "json")
  private NotificationPayload payload;

  private boolean read;

  public static NotificationDeliveryLog create(NotificationLog log, NotificationChannel channel, NotificationPayload payload) {
    NotificationDeliveryLog deliveryLog = new NotificationDeliveryLog();
    deliveryLog.notificationLog = log;
    deliveryLog.channel = channel;
    deliveryLog.sentAt = null;
    deliveryLog.read = false;
    deliveryLog.payload = payload;
    return deliveryLog;
  }

  public void markSent() {
    this.sentAt = LocalDateTime.now();
  }

  public void markRead() {
    this.read = true;
  }
}
