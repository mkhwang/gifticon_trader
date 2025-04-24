package com.example.gifticon_trader.unit.gifticon.infra.impl;

import com.example.gifticon_trader.gifticon.application.GifticonPredicateAssembler;
import com.example.gifticon_trader.gifticon.domain.QGifticon;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonDto;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.example.gifticon_trader.gifticon.infra.impl.GifticonQueryRepositoryImpl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GifticonQueryRepositoryImplUnitTest {

  @Mock
  GifticonPredicateAssembler predicateAssembler;

  @Mock
  JPAQueryFactory queryFactory;

  @InjectMocks
  GifticonQueryRepositoryImpl repository;

  @Mock
  JPAQuery<Long> countQuery;

  @Mock
  JPAQuery<GifticonDto> dataQuery;

  @Test
  void searchGifticons_shouldReturnEmptyPage_whenTotalIsZero() {
    // given
    QGifticon qGifticon = QGifticon.gifticon;

    GifticonSearchDto dto = new GifticonSearchDto();
    BooleanBuilder condition = new BooleanBuilder();
    given(predicateAssembler.build(dto)).willReturn(condition);
    given(queryFactory.select(qGifticon.count())).willReturn(countQuery);
    given(countQuery.from(qGifticon)).willReturn(countQuery);
    given(countQuery.where(condition)).willReturn(countQuery);
    given(countQuery.fetchOne()).willReturn(0L);

    // when
    Page<GifticonDto> result = repository.searchGifticons(dto);

    // then
    verify(predicateAssembler).build(dto);
    assertThat(result.getTotalElements()).isZero();
    assertThat(result.getContent()).isEmpty();
  }
}