package com.example.gifticon_trader.notification.application.handler;

import com.example.gifticon_trader.config.i18n.I18nService;
import com.example.gifticon_trader.notification.application.exception.NotificationDeliveryNotFoundException;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailNotificationHandler extends AbstractNotificationHandler {
  private final JavaMailSender mailSender;
  private final I18nService i18nService;
  private final TemplateEngine templateEngine;
  private final NotificationDeliveryLogRepository deliveryLogRepository;

  @Override
  protected NotificationChannel getChannel() {
    return NotificationChannel.EMAIL;
  }

  @Override
  public void send(long notificationDeliveryId) {
    NotificationDeliveryLog existDeliveryLog = deliveryLogRepository.findById(notificationDeliveryId)
            .orElseThrow(NotificationDeliveryNotFoundException::new);

    if (!existDeliveryLog.getChannel().equals(getChannel())) {
      return;
    }

    EmailPayload emailPayload = (EmailPayload) existDeliveryLog.getPayload();
    Context context = new Context();
    if (!emailPayload.getVariables().isEmpty()) {
      for (String key : emailPayload.getVariables().keySet()) {
        context.setVariable(key, emailPayload.getVariables().get(key));
      }
    }
    String html = templateEngine.process(emailPayload.getTemplateName(), context);

    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      String plainText = i18nService.getMessage(emailPayload.getTemplateName());

      helper.setTo(existDeliveryLog.getUsername());
      helper.setSubject(emailPayload.getSubject());
      helper.setText(plainText, html);
      mailSender.send(message);
    } catch (MessagingException e) {
      // TODO: 메일 전송 실패 시, 재시도 로직 구현
      throw new RuntimeException("메일 전송 실패", e);
    }
  }
}
