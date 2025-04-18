package com.example.gifticon_trader.user.in.rest;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.user.domain.LoginUser;
import com.example.gifticon_trader.user.in.rest.dto.MeDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User Management")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final GenericMapper genericMapper;

  @GetMapping("/api/user/me")
  public MeDto getMe(@AuthenticationPrincipal LoginUser loginUser) {
    return genericMapper.toDto(loginUser, MeDto.class);
  }
}
