package com.example.gifticon_trader.notification.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailPayload extends NotificationPayload {

  private String subject;
  private String templateName;
  private Map<String, Object> variables;
}
