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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminGifticonService {
  private final GifticonQueryRepository gifticonQueryRepository;
  private final GifticonRepository gifticonRepository;
  private final GenericMapper genericMapper;

  @Transactional
  public GifticonDto startInspect(Long id) {
    Gifticon gifticon = gifticonRepository.findById(id).orElseThrow(
            GifticonNotFoundException::new
    );
    gifticon.startInspect();
    return genericMapper.toDto(gifticon, GifticonDto.class);
  }

  @Transactional
  public GifticonDto passInspect(Long id) {
    Gifticon gifticon = gifticonRepository.findById(id).orElseThrow(
            GifticonNotFoundException::new
    );
    gifticon.passInspect();
    return genericMapper.toDto(gifticon, GifticonDto.class);
  }

  @Transactional
  public GifticonDto rejectInspect(Long id, String reason) {
    Gifticon gifticon = gifticonRepository.findById(id).orElseThrow(
            GifticonNotFoundException::new
    );
    gifticon.rejectInspect(reason);
    return genericMapper.toDto(gifticon, GifticonDto.class);
  }

  public Page<GifticonDto> searchGifticons(GifticonSearchDto searchDto) {
    return gifticonQueryRepository.searchGifticons(searchDto);
  }
}
