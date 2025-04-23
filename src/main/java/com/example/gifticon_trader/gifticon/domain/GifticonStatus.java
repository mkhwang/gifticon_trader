package com.example.gifticon_trader.gifticon.domain;

import java.util.List;

public enum GifticonStatus {
  WAIT_FOR_INSPECT, // 검수대기
  INSPECTING, // 검수중
  INSPECT_REJECTED, // 검수반려
  READY_FOR_SALE, // 판매중
  ON_TRADE,  // 거래중
  TRADE_COMPLETED, // 거래완료
  EXPIRED; // 만료

  public boolean canStartInspect() {
    return this == WAIT_FOR_INSPECT;
  }

  public boolean canRejectInspect() {
    return this == INSPECTING;
  }

  public boolean canChangePrice() {
    return !List.of(ON_TRADE, EXPIRED, TRADE_COMPLETED).contains(this);
  }

  public boolean canSettle() {
    return this == TRADE_COMPLETED;
  }

  public boolean canChangeGiftCode() {
    return this == WAIT_FOR_INSPECT;
  }

  public boolean canExpire() {
    return List.of(WAIT_FOR_INSPECT, INSPECTING, INSPECT_REJECTED, READY_FOR_SALE).contains(this);
  }

  public boolean canRequestInspect() {
    return this == INSPECT_REJECTED;
  }

  public boolean canTradeCancel() {
    return this == ON_TRADE;
  }

  public boolean canTradeComplete() {
    return this == ON_TRADE;
  }

  public boolean canTrade() {
    return this == READY_FOR_SALE;
  }
}
