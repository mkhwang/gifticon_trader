package com.example.gifticon_trader.gifticon.infra.impl;

import com.example.gifticon_trader.gifticon.application.GifticonPredicateAssembler;
import com.example.gifticon_trader.gifticon.domain.QGifticon;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.QGifticonDto;
import com.example.gifticon_trader.gifticon.infra.GifticonQueryRepository;
import com.example.gifticon_trader.user.domain.QUser;
import com.example.gifticon_trader.user.in.rest.dto.QUserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GifticonQueryRepositoryImpl implements GifticonQueryRepository {
  private final GifticonPredicateAssembler gifticonPredicateAssembler;
  private final JPAQueryFactory queryFactory;
  private final QGifticon qGifticon = QGifticon.gifticon;
  private final QUser qUser = QUser.user;

  @Override
  public Page<GifticonDto> searchGifticons(GifticonSearchDto searchDto) {
    Pageable pageable = searchDto.toPageable();
    BooleanBuilder condition = gifticonPredicateAssembler.build(searchDto);
    long total = Optional.ofNullable(
                    queryFactory.select(qGifticon.count())
                            .from(qGifticon)
                            .where(condition)
                            .fetchOne())
            .orElse(0L);
    if (total == 0) {
      return Page.empty(pageable);
    }

    List<GifticonDto> result = queryFactory
            .select(new QGifticonDto(
                    qGifticon.id,
                    qGifticon.name,
                    qGifticon.description,
                    qGifticon.originalPrice.amount,
                    qGifticon.price.amount,
                    qGifticon.expirationDate,
                    qGifticon.discountRate,
                    qGifticon.status,
                    new QUserDto(qUser.id, qUser.nickname),
                    qGifticon.createdDate,
                    qUser.lastModifiedDate))
            .from(qGifticon)
            .innerJoin(qGifticon.createdBy, qUser)
            .on(qGifticon.createdBy.id.eq(qUser.id))
            .on(qUser.enable.eq(true))
            .where(condition)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    return new PageImpl<>(result, pageable, total);
  }
}
