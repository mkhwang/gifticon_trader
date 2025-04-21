package com.example.gifticon_trader.unit.user.application;

import com.example.gifticon_trader.user.application.UserSearchService;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserSearchServiceTest {
  @Mock
  UserQueryRepository userQueryRepository;

  @InjectMocks
  UserSearchService userSearchService;

  @Test
  void searchUsers_shouldReturnPageUserDto() {
    // Given
    UserSearchDto userSearchDto = mock(UserSearchDto.class);
    userQueryRepository = mock(UserQueryRepository.class);
    userSearchService = new UserSearchService(userQueryRepository);


    // When
    userSearchService.searchUsers(userSearchDto);

    // Then
    verify(userQueryRepository).searchUsers(userSearchDto);
  }

}