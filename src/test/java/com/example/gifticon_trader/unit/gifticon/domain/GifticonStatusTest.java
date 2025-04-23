package com.example.gifticon_trader.unit.gifticon.domain;

import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GifticonStatusTest {

  @Test
  void testGifticonStatus() {
    // given
    GifticonStatus status = GifticonStatus.READY_FOR_SALE;

    // when
    GifticonStatus status2 = GifticonStatus.valueOf("READY_FOR_SALE");

    // then
    assertEquals(status, status2);
  }

  @Test
  void test_canStartInspect() {
    // given
    GifticonStatus status1 = GifticonStatus.WAIT_FOR_INSPECT;
    GifticonStatus status2 = GifticonStatus.INSPECTING;

    // when & then
    assertTrue(status1.canStartInspect());
    assertFalse(status2.canStartInspect());
  }

  @Test
  void test_canRejectInspect() {
    // given
    GifticonStatus status1 = GifticonStatus.INSPECTING;
    GifticonStatus status2 = GifticonStatus.WAIT_FOR_INSPECT;

    // when & then
    assertTrue(status1.canRejectInspect());
    assertFalse(status2.canRejectInspect());
  }

  @Test
  void test_canChangePrice() {
    // given
    GifticonStatus status1 = GifticonStatus.READY_FOR_SALE;
    GifticonStatus status2 = GifticonStatus.ON_TRADE;

    // when & then
    assertTrue(status1.canChangePrice());
    assertFalse(status2.canChangePrice());
  }

  @Test
  void test_canSettle() {
    // given
    GifticonStatus status1 = GifticonStatus.TRADE_COMPLETED;
    GifticonStatus status2 = GifticonStatus.WAIT_FOR_INSPECT;

    // when & then
    assertTrue(status1.canSettle());
    assertFalse(status2.canSettle());
  }

  void test_canChangeGiftCode() {
    // given
    GifticonStatus status1 = GifticonStatus.WAIT_FOR_INSPECT;
    GifticonStatus status2 = GifticonStatus.INSPECTING;

    // when & then
    assertTrue(status1.canChangeGiftCode());
    assertFalse(status2.canChangeGiftCode());
  }


  @Test
  void test_canExpire() {
    // given
    GifticonStatus status1 = GifticonStatus.WAIT_FOR_INSPECT;
    GifticonStatus status2 = GifticonStatus.EXPIRED;

    // when & then
    assertTrue(status1.canExpire());
    assertFalse(status2.canExpire());
  }
}