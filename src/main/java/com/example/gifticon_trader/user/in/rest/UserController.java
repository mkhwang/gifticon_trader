package com.example.gifticon_trader.user.in.rest;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.user.application.UserSearchService;
import com.example.gifticon_trader.user.domain.LoginUser;
import com.example.gifticon_trader.user.in.rest.dto.MeDto;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User Management")
@RestController
@RequiredArgsConstructor
public class UserController {
  private final GenericMapper genericMapper;
  private final UserSearchService userSearchService;

  @GetMapping("/api/user/me")
  public MeDto getMe(@AuthenticationPrincipal LoginUser loginUser) {
    return genericMapper.toDto(loginUser, MeDto.class);
  }

  @GetMapping("/api/user/search")
  public Page<UserDto> searchUsers(@RequestParam(required = false) UserSearchDto userSearchDto) {
    return userSearchService.searchUsers(userSearchDto);
  }
}
