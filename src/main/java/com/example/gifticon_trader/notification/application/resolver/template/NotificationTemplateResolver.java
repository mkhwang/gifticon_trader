package com.example.gifticon_trader.notification.application.resolver.template;

import com.example.gifticon_trader.notification.domain.NotificationType;

import java.util.Map;

public class NotificationTemplateResolver {

  public static EmailTemplate resolve(NotificationType type, Map<String, Object> params) {
    switch (type) {
      case WELCOME -> {
        return new EmailTemplate("welcome.html", "환영합니다, %s님!".formatted(params.get("nickname")), params);
      }
      case VERIFY_EMAIL -> {
        return new EmailTemplate("email_verification.html", "이메일 인증", params);
      }
      case ADMIN_APPROVED -> {
        return new EmailTemplate("admin_approved.html", "관리자 승인 완료", params);
      }
      case ADMIN_REJECTED -> {
        return new EmailTemplate("admin_rejected.html", "관리자 반려", params);
      }
      case PAYMENT_COMPLETED -> {
        return new EmailTemplate("payment_completed.html", "결제완료", params);
      }
      case GIFTCON_RECEIVED -> {
        return new EmailTemplate("giftcon_received.html", "기프티콘 전달 완료", params);
      }
      case SETTLEMENT_COMPLETE -> {
        return new EmailTemplate("settlement_complete.html", "정산완료", params);
      }
      default -> throw new IllegalArgumentException("Unsupported notification type: " + type);
    }
  }
}
