package com.example.gifticon_trader.unit.gifticon.domain.command;

import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GifticonRegisterCommandTest {

  @Test
  void testGifticonRegisterCommand() {
    // given
    String name = "Test Gifticon";
    String description = "This is a test gifticon.";
    String giftCode = "12345678";
    Long originalPrice = 1000L;
    Long price = 100L;
    LocalDate expirationDate = LocalDate.now();
    List<String> tags = List.of("tag1", "tag2");

    // when
    GifticonRegisterCommand command = new GifticonRegisterCommand(name, description, giftCode,originalPrice, price, expirationDate, tags);

    // then
    assertEquals(name, command.name());
    assertEquals(description, command.description());
    assertEquals(giftCode, command.giftCode());
    assertEquals(originalPrice, command.originalPrice());
    assertEquals(price, command.price());
    assertEquals(expirationDate, command.expirationDate());
  }

}