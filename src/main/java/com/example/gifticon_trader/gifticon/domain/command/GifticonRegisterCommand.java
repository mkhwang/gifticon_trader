package com.example.gifticon_trader.gifticon.domain.command;

import java.time.LocalDate;
import java.util.List;

public record GifticonRegisterCommand(
        String name,
        String description,
        String giftCode,
        Long originalPrice,
        Long price,
        LocalDate expirationDate,
        List<String> tags) { }
