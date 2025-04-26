package com.example.gifticon_trader.gifticon.in.rest;

import com.example.gifticon_trader.config.security.endpoint.IsPublicEndpoint;
import com.example.gifticon_trader.gifticon.application.GifticonRegisterService;
import com.example.gifticon_trader.gifticon.application.GifticonSearchService;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gifticon", description = "Gifticon API")
@RestController
@RequiredArgsConstructor
public class GifticonController {
  private final GifticonRegisterService gifticonRegisterService;
  private final GifticonSearchService gifticonSearchService;

  @Operation(summary = "기프티콘 등록")
  @PostMapping("/api/gifticons")
  public GifticonDto registerGifticon(@RequestBody GifticonRegisterCommand command) {
    return gifticonRegisterService.register(command);
  }

  @Operation(summary = "기프티콘 상세조회")
  @GetMapping("/api/gifticons/{id}")
  public GifticonDto getGificonById(Long id) {
    return gifticonSearchService.getGifticonById(id);
  }

  @Operation(summary = "기프티콘 검색")
  @IsPublicEndpoint
  @GetMapping("/api/gifticons/search")
  public Page<GifticonDto> searchGifticons(@ModelAttribute GifticonSearchDto searchDto) {
    return gifticonSearchService.searchGifticons(searchDto);
  }
}
