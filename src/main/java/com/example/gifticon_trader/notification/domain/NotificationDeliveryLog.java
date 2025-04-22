package com.example.gifticon_trader.notification.domain;

import com.example.gifticon_trader.notification.domain.payload.NotificationPayload;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Entity
public class NotificationDeliveryLog {
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

  @CreatedDate
  @Column(updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  private Instant lastModifiedDate;

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

  public String getUsername() {
    return notificationLog.getUser().getUsername();
  }
}
