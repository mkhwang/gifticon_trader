package com.example.gifticon_trader.unit.notification.application.resolver.payload.assembler;

import com.example.gifticon_trader.notification.application.resolver.payload.assembler.EmailNotificationPayloadAssembler;
import com.example.gifticon_trader.notification.application.resolver.payload.email.EmailNotificationPayloadResolver;
import com.example.gifticon_trader.notification.application.resolver.payload.email.WelcomeEmailNotificationResolver;
import com.example.gifticon_trader.notification.domain.NotificationChannel;
import com.example.gifticon_trader.notification.domain.NotificationType;
import com.example.gifticon_trader.notification.domain.dto.NotificationLogRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationPayloadAssemblerTest {
  @Mock
  WelcomeEmailNotificationResolver emailNotificationPayloadResolver;

  @Spy
  List<EmailNotificationPayloadResolver> emailNotificationPayloadResolvers = new ArrayList<>();

  @InjectMocks
  EmailNotificationPayloadAssembler emailNotificationPayloadAssembler;

  @BeforeEach
  void setUp() {
    emailNotificationPayloadResolvers.add(emailNotificationPayloadResolver);
  }

  @Test
  void supports_shouldReturnTrueForEmailChannel() {
    // given
    NotificationChannel channel1 = NotificationChannel.EMAIL;
    NotificationChannel channel2 = NotificationChannel.SOCKET;

    // when
    boolean result1 = emailNotificationPayloadAssembler.supports(channel1);
    boolean result2 = emailNotificationPayloadAssembler.supports(channel2);

    // then
    assertTrue(result1);
    assertFalse(result2);
  }

  @Test
  void assemble_shouldReturnNotificationPayload() {
    // given
    NotificationLogRecord welcomeRecord = new NotificationLogRecord(
            "title",
            "message",
            NotificationType.WELCOME,
            null
    );
    given(emailNotificationPayloadResolver.supports(NotificationType.WELCOME)).willReturn(true);

    // when
    emailNotificationPayloadAssembler.assemble(welcomeRecord);

    //then
    verify(emailNotificationPayloadResolver).resolve(welcomeRecord);
  }
}
