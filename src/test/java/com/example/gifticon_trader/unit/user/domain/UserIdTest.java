package com.example.gifticon_trader.unit.user.domain;

import com.example.gifticon_trader.user.domain.UserId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserIdTest {

  @Test
  void testUserIdCreation() {
    // Given
    Long id = 1234L;

    // When
    UserId userId = new UserId(id);

    // Then
    assertEquals(id, userId.getValue());
  }


  @Test
  void testUserIdEquals() {
    // Given
    UserId userId1 = new UserId(1234L);
    UserId userId2 = new UserId(1234L);
    UserId userId3 = new UserId(5678L);

    // When & Then
    assertEquals(userId1, userId2);
    assertNotEquals(userId1, userId3);
  }
}