package com.example.gifticon_trader.notification.domain;

import com.example.gifticon_trader.config.security.audit.AuditBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
  private Map<String, Object> option;

  private boolean read;

  public static NotificationDeliveryLog create(NotificationLog log, NotificationChannel channel) {
    NotificationDeliveryLog deliveryLog = new NotificationDeliveryLog();
    deliveryLog.notificationLog = log;
    deliveryLog.channel = channel;
    deliveryLog.sentAt = null;
    deliveryLog.read = false;
    deliveryLog.option = new HashMap<>();
    return deliveryLog;
  }

  public void sent() {
    this.sentAt = LocalDateTime.now();
  }

  public void read() {
    this.read = true;
  }

  public void addOption(String key, Object value) {
    this.option.put(key, value);
  }
}
