package com.example.gifticon_trader.unit.gifticon.in.rest;

import com.example.gifticon_trader.gifticon.application.GifticonRegisterService;
import com.example.gifticon_trader.gifticon.application.GifticonSearchService;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.in.rest.GifticonController;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GifticonControllerTest {
  @Mock
  GifticonRegisterService gifticonRegisterService;

  @Mock
  GifticonSearchService gifticonSearchService;


  @InjectMocks
  GifticonController gifticonController;

  @Test
  void registerGifticon() {
    // given
    GifticonRegisterCommand command = mock(GifticonRegisterCommand.class);
    GifticonDto gifticonDto = mock(GifticonDto.class);
    given(gifticonRegisterService.register(command)).willReturn(gifticonDto);

    // when
    GifticonDto result = gifticonController.registerGifticon(command);

    // then
    verify(gifticonRegisterService).register(command);
    assertEquals(gifticonDto, result);
  }

  @Test
  void getGificonById() {
    // given
    Long id = 1L;
    GifticonDto gifticonDto = mock(GifticonDto.class);
    given(gifticonSearchService.getGifticonById(id)).willReturn(gifticonDto);

    // when
    GifticonDto result = gifticonController.getGificonById(id);

    // then
    verify(gifticonSearchService).getGifticonById(id);
    assertEquals(gifticonDto, result);
  }

  @Test
  void searchGifticon() {
    // given
    GifticonSearchDto searchDto = mock(GifticonSearchDto.class);
    @SuppressWarnings("unchecked")
    Page<GifticonDto> gifticonPage = mock(Page.class);
    given(gifticonSearchService.searchGifticons(searchDto)).willReturn(gifticonPage);

    // when
    Page<GifticonDto> result = gifticonController.searchGifticons(searchDto);

    // then
    verify(gifticonSearchService).searchGifticons(searchDto);
    assertEquals(gifticonPage, result);
  }
}