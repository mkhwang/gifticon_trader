package com.example.gifticon_trader.notification.application.handler;

import com.example.gifticon_trader.notification.domain.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class KaKaoNotificationHandler extends AbstractNotificationHandler{

  @Override
  protected NotificationChannel getChannel() {
    return NotificationChannel.KAKAO;
  }

  @Override
  public void send(long notificationDeliveryId) {
    // TODO: Implement email sending logic
  }
}
