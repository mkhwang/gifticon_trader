package com.example.gifticon_trader.user.infra.impl;

import com.example.gifticon_trader.user.domain.QUser;
import com.example.gifticon_trader.user.in.rest.dto.QUserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<UserDto> searchUsers(UserSearchDto searchDto) {
    QUser user = QUser.user;
    Pageable pageable = searchDto.toPageable();

    BooleanBuilder searchCondition = this.buildSearchCondition(searchDto);

    long total = Optional.ofNullable(queryFactory
            .select(user.count())
            .from(user)
            .where(searchCondition)
            .fetchOne()).orElse(0L);
    if (total == 0) {
      return new PageImpl<>(List.of(), pageable, 0);
    }

    List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable, user.getType(), user.getMetadata().getName());

    List<UserDto> result = queryFactory
            .select(new QUserDto(user.id, user.nickname))
            .from(user)
            .where(searchCondition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .fetch();

    return new PageImpl<>(result, pageable, total);
  }

  private BooleanBuilder buildSearchCondition(UserSearchDto searchDto) {
    BooleanBuilder builder = new BooleanBuilder();
    if (StringUtils.hasText(searchDto.getNickname())) {
      builder.and(QUser.user.nickname.containsIgnoreCase(searchDto.getNickname()));
    }
    return builder;
  }


  private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable, Class<?> entityClass, String alias) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    PathBuilder<?> pathBuilder = new PathBuilder<>(entityClass, alias);

    for (Sort.Order order : pageable.getSort()) {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;

      // ComparableExpression으로 캐스팅
      ComparableExpression<?> expression = pathBuilder.getComparable(order.getProperty(), Comparable.class);
      orderSpecifiers.add(new OrderSpecifier<>(direction, expression));
    }

    return orderSpecifiers;
  }
}
