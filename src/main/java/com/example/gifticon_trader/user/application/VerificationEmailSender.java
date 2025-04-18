package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.config.i18n.I18nService;
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
public class VerificationEmailSender {

  private final JavaMailSender mailSender;
  private final I18nService i18nService;
  private final TemplateEngine templateEngine;

  public void send(String to, String token) {
    // TODO : 이메일 발송 시, 실제 도메인으로 변경
    String verificationLink = "http://localhost:8080/verify-email-complete?token=" + token;

    Context context = new Context();
    context.setVariable("verificationLink", verificationLink);
    context.setVariable("username", to);
    String html = templateEngine.process("email-verification", context);

    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      String plainText = i18nService.getMessage("user.email.verify.email.plain.text", verificationLink);

      helper.setTo(to);
      helper.setSubject("Verify your email");
      helper.setText(plainText, html);
      mailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("메일 전송 실패", e);
    }
  }
}