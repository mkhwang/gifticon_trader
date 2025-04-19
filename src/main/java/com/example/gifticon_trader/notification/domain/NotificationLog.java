package com.example.gifticon_trader.notification.domain;

import com.example.gifticon_trader.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
public class NotificationLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private String title;

  private String message;

  private Long referenceId;

  private LocalDateTime createdAt;

  protected NotificationLog() {}

  public static NotificationLog create(User user, NotificationType notificationType, String title, String message) {
    NotificationLog notificationLog = new NotificationLog();
    notificationLog.user = user;
    notificationLog.notificationType = notificationType;
    notificationLog.title = title;
    notificationLog.message = message;
    return notificationLog;
  }

  public static NotificationLog create(User user, NotificationType notificationType, String title, String message, Long referenceId) {
    NotificationLog notificationLog = create(user, notificationType, title, message);
    notificationLog.referenceId = referenceId;
    return notificationLog;
  }

  public Set<NotificationChannel> getNotificationChannels() {
    return this.notificationType.getSupportedChannels();
  }
}
