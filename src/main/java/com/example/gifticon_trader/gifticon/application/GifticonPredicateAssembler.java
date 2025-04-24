package com.example.gifticon_trader.gifticon.application;

import com.example.gifticon_trader.common.querydsl.AbstractPredicateAssembler;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import com.example.gifticon_trader.gifticon.domain.QGifticon;
import com.example.gifticon_trader.gifticon.in.rest.dto.GifticonSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GifticonPredicateAssembler extends AbstractPredicateAssembler<GifticonSearchDto> {

  private final QGifticon gifticon = QGifticon.gifticon;

  @Override
  protected List<BooleanExpression> collect(GifticonSearchDto condition) {
    List<BooleanExpression> expressions = new ArrayList<>();

    append(expressions, keywordContains(condition.getKeyword()));
    append(expressions, statusIn(condition.getStatuses()));
    append(expressions, expirationAfter(condition.getStartDate()));
    append(expressions, expirationBefore(condition.getEndDate()));
    append(expressions, sellerIdEq(condition.getSellerId()));

    return expressions;
  }

  private BooleanExpression keywordContains(String keyword) {
    if (!StringUtils.hasText(keyword)) return null;
    return gifticon.name.containsIgnoreCase(keyword)
            .or(gifticon.description.containsIgnoreCase(keyword))
            .or(gifticon.createdBy.nickname.containsIgnoreCase(keyword));
  }

  private BooleanExpression statusIn(List<GifticonStatus> statuses) {
    return statuses != null && !statuses.isEmpty() ? gifticon.status.in(statuses) : null;
  }

  private BooleanExpression expirationAfter(LocalDate startDate) {
    return startDate != null ? gifticon.expirationDate.goe(startDate) : null;
  }

  private BooleanExpression expirationBefore(LocalDate endDate) {
    return endDate != null ? gifticon.expirationDate.loe(endDate) : null;
  }

  private BooleanExpression sellerIdEq(Long sellerId) {
    return sellerId != null ? gifticon.createdBy.id.eq(sellerId) : null;
  }
}
