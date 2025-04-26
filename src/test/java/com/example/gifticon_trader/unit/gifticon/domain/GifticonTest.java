package com.example.gifticon_trader.unit.gifticon.domain;

import com.example.gifticon_trader.gifticon.application.exception.InvalidExpirationDateException;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GifticonTest {
  @Mock
  GifticonRegisterCommand command;

  @BeforeEach
  void setUp() {
    given(command.name()).willReturn("Test Gifticon");
    given(command.description()).willReturn("This is a test gifticon.");
    given(command.originalPrice()).willReturn(1000L);
    given(command.price()).willReturn(100L);
    given(command.expirationDate()).willReturn(LocalDate.now());
    given(command.giftCode()).willReturn("123456789");
    given(command.tags()).willReturn(List.of("tag1", "tag2"));
  }

  @Test
  void test_GifticonCreation() {
    // given


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

  @Test
  void test_GifticonCreation_shouldThrowException_when_expirationDateIsPast() {
    // given
    given(command.expirationDate()).willReturn(LocalDate.now().minusDays(1));

    // when & then
    assertThatThrownBy(() -> Gifticon.fromCommand(command))
            .isInstanceOf(InvalidExpirationDateException.class);
  }
}