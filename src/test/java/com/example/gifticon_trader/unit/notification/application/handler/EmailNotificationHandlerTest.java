package com.example.gifticon_trader.unit.notification.application.handler;

import com.example.gifticon_trader.config.i18n.I18nService;
import com.example.gifticon_trader.notification.application.handler.EmailNotificationHandler;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationDeliveryLog;
import com.example.gifticon_trader.notification.domain.payload.EmailPayload;
import com.example.gifticon_trader.notification.infra.NotificationDeliveryLogRepository;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationHandlerTest {

  @Mock
  JavaMailSender mailSender;
  @Mock
  I18nService i18nService;
  @Mock
  TemplateEngine templateEngine;
  @Mock
  NotificationDeliveryLogRepository deliveryLogRepository;

  @InjectMocks
  EmailNotificationHandler handler;

  @Test
  void send_shouldSendEmail_whenValidPayloadProvided() throws Exception {
    // Given
    long deliveryId = 1L;
    EmailPayload payload = new EmailPayload(
            "subject", "template.html",  Map.of("username", "user"));

    NotificationDeliveryLog log = mock(NotificationDeliveryLog.class);
    given(log.getChannel()).willReturn(NotificationChannel.EMAIL);
    given(log.getPayload()).willReturn(payload);
    given(log.getUsername()).willReturn("test@example.com");

    given(deliveryLogRepository.findById(deliveryId)).willReturn(Optional.of(log));

    String renderedHtml = "<html>Rendered</html>";
    given(templateEngine.process(eq("template.html"), any(Context.class))).willReturn(renderedHtml);
    given(i18nService.getMessage("template.html")).willReturn("Welcome!");

    MimeMessage mimeMessage = new MimeMessage((Session) null);
    given(mailSender.createMimeMessage()).willReturn(mimeMessage);

    // When
    handler.send(deliveryId);

    // Then
    verify(mailSender).send(any(MimeMessage.class));
  }

  @Test
  void send_shouldNotSendEmail_whenChannelMismatch() {
    // Given
    long deliveryId = 2L;
    NotificationDeliveryLog log = mock(NotificationDeliveryLog.class);
    given(log.getChannel()).willReturn(NotificationChannel.FCM); // not EMAIL

    given(deliveryLogRepository.findById(deliveryId)).willReturn(Optional.of(log));

    // When
    handler.send(deliveryId);

    // Then
    verify(mailSender, never()).send((MimeMessage) any());
  }

  @Test
  void send_shouldThrowException_whenMessageSendFails() {
    // Given
    long deliveryId = 3L;
    EmailPayload payload = new EmailPayload("subject", "template.html", Map.of());

    NotificationDeliveryLog log = mock(NotificationDeliveryLog.class);
    given(log.getChannel()).willReturn(NotificationChannel.EMAIL);
    given(log.getPayload()).willReturn(payload);
    given(log.getUsername()).willReturn("test@example.com");
    given(deliveryLogRepository.findById(deliveryId)).willReturn(Optional.of(log));

    given(templateEngine.process(eq("template.html"), any(Context.class))).willReturn("<html></html>");
    given(i18nService.getMessage("template.html")).willReturn("Hello");

    MimeMessage message = mock(MimeMessage.class);
    given(mailSender.createMimeMessage()).willReturn(message);

    doThrow(new MailSendException("메일 전송 실패"))
            .when(mailSender).send(message);

    // Then
    assertThatThrownBy(() -> handler.send(deliveryId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("메일 전송 실패");
  }
}