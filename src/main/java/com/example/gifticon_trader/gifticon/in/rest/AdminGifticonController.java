package com.example.gifticon_trader.gifticon.in.rest;

import com.example.gifticon_trader.gifticon.application.AdminGifticonService;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminGifticonController {
  private final AdminGifticonService adminGifticonService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/api/admin/gifticons/search")
  public Page<GifticonDto> registerGifticon(GifticonSearchDto searchDto) {
    return adminGifticonService.searchGifticons(searchDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/api/admin/gifticons/{id}/inspect/start")
  public GifticonDto getGificonById(Long id) {
    return adminGifticonService.startInspect(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/api/admin/gifticons/{id}/inspect/pass")
  public GifticonDto passInspect(Long id) {
    return adminGifticonService.passInspect(id);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/api/admin/gifticons/{id}/inspect/reject")
  public GifticonDto rejectInspect(Long id, String reason) {
    return adminGifticonService.rejectInspect(id, reason);
  }
}
