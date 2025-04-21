package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserQueryRepository;
import com.example.gifticon_trader.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSearchService {
  private final UserQueryRepository userQueryRepository;
  private final UserRepository userRepository;
  private final GenericMapper genericMapper;


  public Page<UserDto> searchUsers(UserSearchDto userSearchDto) {
    return userQueryRepository.searchUsers(userSearchDto);
  }

  public UserDto getUserProfile(Long id) {
    User foundUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    return genericMapper.toDto(foundUser, UserDto.class);
  }
}
