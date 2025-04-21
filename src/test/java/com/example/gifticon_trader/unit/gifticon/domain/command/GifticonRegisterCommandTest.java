package com.example.gifticon_trader.unit.gifticon.domain.command;

import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GifticonRegisterCommandTest {

  @Test
  void testGifticonRegisterCommand() {
    // given
    String name = "Test Gifticon";
    String description = "This is a test gifticon.";
    Long originalPrice = 1000L;
    Long price = 100L;
    LocalDate expirationDate = LocalDate.now();

    // when
    GifticonRegisterCommand command = new GifticonRegisterCommand(name, description, originalPrice, price, expirationDate);

    // then
    assertEquals(name, command.getName());
    assertEquals(description, command.getDescription());
    assertEquals(originalPrice, command.getOriginalPrice());
    assertEquals(price, command.getPrice());
    assertEquals(expirationDate, command.getExpirationDate());
    assertEquals(90f, command.getDiscountRate());
  }

}