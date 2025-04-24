package com.example.gifticon_trader.gifticon.application;


import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.gifticon.application.exception.GifticonNotFoundException;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.example.gifticon_trader.gifticon.infra.GifticonQueryRepository;
import com.example.gifticon_trader.gifticon.infra.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GifticonSearchService {
  private final GifticonRepository gifticonRepository;
  private final GifticonQueryRepository gifticonQueryRepository;
  private final GenericMapper genericMapper;

  // 일반 사용자에게 노출 가능한 상태만 필터링
  private final List<GifticonStatus> allowedStatuses = List.of(
          GifticonStatus.READY_FOR_SALE,
          GifticonStatus.EXPIRED,
          GifticonStatus.ON_TRADE,
          GifticonStatus.TRADE_COMPLETED
  );

  public GifticonDto getGifticonById(Long id) {
    Gifticon gifticon = gifticonRepository.findById(id)
            .orElseThrow(GifticonNotFoundException::new);

    return genericMapper.toDto(gifticon, GifticonDto.class);
  }

  public Page<GifticonDto> searchGifticons(GifticonSearchDto searchDto) {
    return gifticonQueryRepository.searchGifticons(sanitizeGifticonSearchDto(searchDto));
  }

  // 일반 사용자에게 노출 가능한 검색조건 필터링
  private GifticonSearchDto sanitizeGifticonSearchDto(GifticonSearchDto searchDto) {
    searchDto.setStatuses(sanitizeGifticonStatus(searchDto.getStatuses()));
    return searchDto;
  }

  private List<GifticonStatus> sanitizeGifticonStatus(List<GifticonStatus> statuses) {
    if (statuses == null || statuses.isEmpty()) {
      return List.of(GifticonStatus.READY_FOR_SALE);
    }
    return statuses.stream()
            .filter(allowedStatuses::contains)
            .toList();
  }
}
