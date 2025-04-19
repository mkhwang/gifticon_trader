package com.example.gifticon_trader.notification.domain;

import com.example.gifticon_trader.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

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
}
