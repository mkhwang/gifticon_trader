package com.example.gifticon_trader.unit.gifticon.application;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.gifticon.application.GifticonSearchService;
import com.example.gifticon_trader.gifticon.application.exception.GifticonNotFoundException;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.example.gifticon_trader.gifticon.infra.GifticonQueryRepository;
import com.example.gifticon_trader.gifticon.infra.GifticonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GifticonSearchServiceTest {

  @Mock
  GifticonRepository gifticonRepository;

  @Mock
  GifticonQueryRepository gifticonQueryRepository;

  @Mock
  GenericMapper genericMapper;

  @InjectMocks
  GifticonSearchService gifticonSearchService;

  @Test
  void getGifticonById_shouldReturnDto_whenGifticonExists() {
    // given
    Long id = 1L;
    Gifticon mockGifticon = mock(Gifticon.class);
    GifticonDto expectedDto = new GifticonDto();

    given(gifticonRepository.findById(id)).willReturn(Optional.of(mockGifticon));
    given(genericMapper.toDto(mockGifticon, GifticonDto.class)).willReturn(expectedDto);

    // when
    GifticonDto result = gifticonSearchService.getGifticonById(id);

    // then
    verify(gifticonRepository).findById(id);
    verify(genericMapper).toDto(mockGifticon, GifticonDto.class);
    assertThat(result).isEqualTo(expectedDto);
  }

  @Test
  void getGifticonById_shouldThrowException_whenGifticonNotFound() {
    // given
    Long id = 99L;
    given(gifticonRepository.findById(id)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> gifticonSearchService.getGifticonById(id))
            .isInstanceOf(GifticonNotFoundException.class);

    verify(gifticonRepository).findById(id);
    verify(genericMapper, never()).toDto(null, GifticonDto.class);
  }

  @Test
  void searchGifticons_shouldReturnGifditonDto() {
    // given
    GifticonSearchDto dto = mock(GifticonSearchDto.class);
    @SuppressWarnings("unchecked")
    Page<GifticonDto> mockPage = mock(Page.class);
    given(gifticonQueryRepository.searchGifticons(dto)).willReturn(mockPage);

    // when
    Page<GifticonDto> result = gifticonQueryRepository.searchGifticons(dto);

    // then
    verify(gifticonQueryRepository).searchGifticons(dto);
    assertThat(result).isEqualTo(mockPage);
  }
}