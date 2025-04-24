package com.example.gifticon_trader.gifticon.application;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.gifticon.domain.Gifticon;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.infra.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GifticonRegisterService {
  private final GifticonRepository gifticonRepository;
  private final GenericMapper genericMapper;

  @Transactional
  public GifticonDto register(GifticonRegisterCommand command) {
    Gifticon gifticon = Gifticon.fromCommand(command);
    gifticonRepository.save(gifticon);
    return genericMapper.toDto(gifticon, GifticonDto.class);
  }
}
