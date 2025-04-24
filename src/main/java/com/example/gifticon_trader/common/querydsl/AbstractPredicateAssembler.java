package com.example.gifticon_trader.common.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import java.util.Objects;

public abstract class AbstractPredicateAssembler<T> {

  public BooleanBuilder build(T condition) {
    BooleanBuilder builder = new BooleanBuilder();
    List<BooleanExpression> expressions = collect(condition);
    expressions.stream()
            .filter(Objects::nonNull)
            .forEach(builder::and);
    return builder;
  }

  protected abstract List<BooleanExpression> collect(T condition);

  protected void append(List<BooleanExpression> list, BooleanExpression expr) {
    if (expr != null) list.add(expr);
  }
}