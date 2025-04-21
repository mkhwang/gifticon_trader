package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {
  private final UserQueryRepository userQueryRepository;


  public Page<UserDto> searchUsers(UserSearchDto userSearchDto) {
    return userQueryRepository.searchUsers(userSearchDto);
  }
}
