package com.example.gifticon_trader.unit.gifticon.application;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.gifticon.application.GifticonRegisterService;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.infra.GifticonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GifticonRegisterServiceTest {
  @Mock
  GifticonRepository gifticonRepository;

  @Mock
  GenericMapper genericMapper;

  @InjectMocks
  GifticonRegisterService gifticonRegisterService;


  @Test
  void register() {
    // given
    GifticonRegisterCommand command = mock(GifticonRegisterCommand.class);
    Gifticon expectedGifticon = mock(Gifticon.class);
    GifticonDto expectedDto = new GifticonDto();

    try (MockedStatic<Gifticon> mockedStatic = mockStatic(Gifticon.class)) {
      mockedStatic.when(() -> Gifticon.fromCommand(command))
              .thenReturn(expectedGifticon);

      given(gifticonRepository.save(expectedGifticon)).willReturn(expectedGifticon);
      given(genericMapper.toDto(expectedGifticon, GifticonDto.class)).willReturn(expectedDto);

      // when
      GifticonDto result = gifticonRegisterService.register(command);

      // then
      mockedStatic.verify(() -> Gifticon.fromCommand(command));
      verify(gifticonRepository).save(expectedGifticon);
      verify(genericMapper).toDto(expectedGifticon, GifticonDto.class);
      assertThat(result).isEqualTo(expectedDto);
    }
  }
}