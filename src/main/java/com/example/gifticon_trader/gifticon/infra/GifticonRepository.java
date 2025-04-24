package com.example.gifticon_trader.gifticon.infra;

import com.example.gifticon_trader.gifticon.domain.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
}
