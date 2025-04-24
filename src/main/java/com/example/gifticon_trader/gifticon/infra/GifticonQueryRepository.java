package com.example.gifticon_trader.gifticon.infra;

import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import org.springframework.data.domain.Page;

public interface GifticonQueryRepository {

  Page<GifticonDto> searchGifticons(GifticonSearchDto searchDto);
}
