package com.example.gifticon_trader.user.domain.event;

public record VerificationEmailEvent (Long userId, Long verificationId) { }
