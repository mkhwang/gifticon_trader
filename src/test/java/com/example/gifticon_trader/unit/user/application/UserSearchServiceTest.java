package com.example.gifticon_trader.unit.user.application;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.user.application.UserSearchService;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserQueryRepository;
import com.example.gifticon_trader.user.infra.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserSearchServiceTest {
  @Mock
  UserQueryRepository userQueryRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  GenericMapper genericMapper;

  @InjectMocks
  UserSearchService userSearchService;

  @Test
  void searchUsers_shouldReturnPageUserDto() {
    // Given
    UserSearchDto userSearchDto = mock(UserSearchDto.class);
    userQueryRepository = mock(UserQueryRepository.class);
    userRepository = mock(UserRepository.class);
    genericMapper = mock(GenericMapper.class);
    userSearchService = new UserSearchService(userQueryRepository, userRepository, genericMapper);


    // When
    userSearchService.searchUsers(userSearchDto);

    // Then
    verify(userQueryRepository).searchUsers(userSearchDto);
  }

  @Test
  void getUserProfile_shouldReturnUserDto() {
    // Given
    Long id = 1L;
    userQueryRepository = mock(UserQueryRepository.class);
    userRepository = mock(UserRepository.class);
    genericMapper = mock(GenericMapper.class);
    userSearchService = new UserSearchService(userQueryRepository, userRepository, genericMapper);
    given(userRepository.findById(id)).willReturn(Optional.of(mock(User.class)));

    // When
    userSearchService.getUserProfile(id);

    // Then
    verify(userRepository).findById(id);
  }

}