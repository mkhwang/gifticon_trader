package com.example.gifticon_trader.unit.user.infra.impl;

import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.impl.UserQueryRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserQueryRepositoryImplTest {

  @InjectMocks
  UserQueryRepositoryImpl userQueryRepository;

  @Mock
  JPAQueryFactory jpaQueryFactory;

  @BeforeEach
  void setUp() {
    jpaQueryFactory = mock(JPAQueryFactory.class);
    userQueryRepository = new UserQueryRepositoryImpl(jpaQueryFactory);
  }

  @Test
  void searchUsers_shouldReturnPageUserDto() {
    // Given
    UserSearchDto userSearchDto = mock(UserSearchDto.class);
    given(userQueryRepository.searchUsers(userSearchDto)).willReturn(mock(Page.class));

    // When
    userQueryRepository.searchUsers(userSearchDto);

    // Then
    verify(userQueryRepository).searchUsers(userSearchDto);
  }
}