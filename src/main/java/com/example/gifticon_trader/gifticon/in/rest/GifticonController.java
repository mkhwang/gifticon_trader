package com.example.gifticon_trader.gifticon.in.rest;

import com.example.gifticon_trader.gifticon.application.GifticonRegisterService;
import com.example.gifticon_trader.gifticon.application.GifticonSearchService;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GifticonController {
  private final GifticonRegisterService gifticonRegisterService;
  private final GifticonSearchService gifticonSearchService;

  @PostMapping("/api/gifticons")
  public GifticonDto registerGifticon(GifticonRegisterCommand command) {
    return gifticonRegisterService.register(command);
  }

  @GetMapping("/api/gifticons/{id}")
  public GifticonDto getGificonById(Long id) {
    return gifticonSearchService.getGifticonById(id);
  }
}
