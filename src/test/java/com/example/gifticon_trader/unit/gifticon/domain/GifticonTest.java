package com.example.gifticon_trader.unit.gifticon.domain;

import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GifticonTest {

  @Test
  void testGifticonCreation() {
    // given
    GifticonRegisterCommand command = mock(GifticonRegisterCommand.class);
    given(command.getName()).willReturn("Test Gifticon");
    given(command.getDescription()).willReturn("This is a test gifticon.");
    given(command.getOriginalPrice()).willReturn(1000L);
    given(command.getPrice()).willReturn(100L);
    given(command.getDiscountRate()).willReturn(90f);
    given(command.getExpirationDate()).willReturn(LocalDate.now());

    // when
    Gifticon gifticon = Gifticon.fromCommand(command);

    // then
    assertThat(gifticon).isNotNull();
    assertThat(gifticon.getStatus())
        .isEqualTo(GifticonStatus.WAIT_FOR_INSPECT);
    assertThat(gifticon.getOriginalPrice().getAmount()).isEqualTo(1000L);
    assertThat(gifticon.getPrice().getAmount()).isEqualTo(100L);
    assertThat(gifticon.getExpirationDate()).isEqualTo(LocalDate.now());
    assertThat(gifticon.getDiscountRate()).isEqualTo(90f);
  }
}