package com.example.gifticon_trader.gifticon.application;


import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.gifticon.application.exception.GifticonNotFoundException;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.example.gifticon_trader.gifticon.infra.GifticonQueryRepository;
import com.example.gifticon_trader.gifticon.infra.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GifticonSearchService {
  private final GifticonRepository gifticonRepository;
  private final GifticonQueryRepository gifticonQueryRepository;
  private final GenericMapper genericMapper;

  public GifticonDto getGifticonById(Long id) {
    Gifticon gifticon = gifticonRepository.findById(id)
            .orElseThrow(GifticonNotFoundException::new);

    return genericMapper.toDto(gifticon, GifticonDto.class);
  }

  public Page<GifticonDto> searchGifticons(GifticonSearchDto searchDto) {
    return gifticonQueryRepository.searchGifticons(searchDto);
  }
}
