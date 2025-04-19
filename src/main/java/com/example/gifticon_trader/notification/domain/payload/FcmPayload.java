package com.example.gifticon_trader.notification.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FcmPayload extends NotificationPayload{

  private String title;
  private String body;
  private String deepLink;
}
