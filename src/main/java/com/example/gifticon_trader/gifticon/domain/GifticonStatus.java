package com.example.gifticon_trader.gifticon.domain;

public enum GifticonStatus {
  WAIT_FOR_INSPECT, // 검수대기
  INSPECTING, // 검수중
  INSPECT_REJECTED, // 검수반려
  READY_FOR_SALE, // 판매중
  ON_TRADE,  // 구매중
  EXPIRED, // 만료
}
