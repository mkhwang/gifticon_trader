package com.example.gifticon_trader.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PageRequestDto {

  private Integer page = 1;

  private Integer size = 20;

  @Schema(description = "정렬 필드 목록. 예: createdDate,desc", example = "createdDate,desc")
  private List<String> sort;

  public Pageable toPageable() {
    int safePage = (this.page == null || this.page < 1) ? 1 : this.page;
    int safeSize = (this.size == null || this.size < 1) ? 20 : this.size;

    List<Sort.Order> orders = parseSorts();
    return orders.isEmpty()
            ? PageRequest.of(safePage - 1, safeSize)
            : PageRequest.of(safePage - 1, safeSize, Sort.by(orders));
  }

  private List<Sort.Order> parseSorts() {
    if (sort == null || sort.isEmpty()) return Collections.emptyList();

    return sort.stream()
            .map(s -> {
              String[] parts = s.split(",");
              if (parts.length == 2) {
                return new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]);
              }
              return null;
            })
            .filter(Objects::nonNull)
            .toList();
  }
}
