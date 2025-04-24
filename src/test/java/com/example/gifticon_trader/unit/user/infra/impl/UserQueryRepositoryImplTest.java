package com.example.gifticon_trader.unit.user.infra.impl;

import com.example.gifticon_trader.user.domain.QUser;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.impl.UserQueryRepositoryImpl;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class UserQueryRepositoryImplTest {

  @Mock
  JPAQueryFactory queryFactory;

  @Mock
  JPAQuery<Long> countQuery;

  @Mock
  JPAQuery<UserDto> resultQuery;

  @InjectMocks
  UserQueryRepositoryImpl userQueryRepository;

  @Test
  void searchUsers_shouldReturnPagedUserDtoList() {
    // given
    QUser user = QUser.user;
    UserSearchDto searchDto = new UserSearchDto();
    searchDto.setNickname("tester");
    Pageable pageable = searchDto.toPageable();
    NumberExpression<Long> countExpr = user.count();
    ConstructorExpression<UserDto> constructor = Projections.constructor(UserDto.class, user.id, user.nickname);

    given(queryFactory.select(countExpr)).willReturn(countQuery);
    given(countQuery.from(user)).willReturn(countQuery);
    given(countQuery.where(any(Predicate.class))).willReturn(countQuery);
    given(countQuery.fetchOne()).willReturn(1L);

    given(queryFactory.select(constructor)).willReturn(resultQuery);
    given(resultQuery.from(user)).willReturn(resultQuery);
    given(resultQuery.where((Predicate) any())).willReturn(resultQuery);
    given(resultQuery.offset(pageable.getOffset())).willReturn(resultQuery);
    given(resultQuery.limit(pageable.getPageSize())).willReturn(resultQuery);
    given(resultQuery.orderBy(any(OrderSpecifier[].class))).willReturn(resultQuery);
    given(resultQuery.fetch()).willReturn(List.of(new UserDto(1L, "tester")));

    // when
    Page<UserDto> result = userQueryRepository.searchUsers(searchDto);

    // then
    assertThat(result.getTotalElements()).isEqualTo(1);
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).getNickname()).isEqualTo("tester");
  }
}