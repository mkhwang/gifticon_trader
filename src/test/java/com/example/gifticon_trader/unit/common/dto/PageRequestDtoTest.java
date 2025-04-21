package com.example.gifticon_trader.unit.common.dto;

import com.example.gifticon_trader.common.dto.PageRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PageRequestDtoTest {

  @Test
  void toPageable_shouldReturnPageRequestWithSort() {
    PageRequestDto dto = new PageRequestDto();
    dto.setPage(1);
    dto.setSize(20);
    dto.setSort(List.of("createdDate,desc", "id,asc"));

    Pageable pageable = dto.toPageable();

    assertThat(pageable.getPageNumber()).isEqualTo(0);
    assertThat(pageable.getPageSize()).isEqualTo(20);

    List<Sort.Order> orders = pageable.getSort().toList();
    assertThat(orders).hasSize(2);
    assertThat(orders).extracting("property").containsExactly("createdDate", "id");
    assertThat(orders).extracting("direction").containsExactly(Sort.Direction.DESC, Sort.Direction.ASC);
  }

  @Test
  void toPageable_shouldHandleEmptySort() {
    PageRequestDto dto = new PageRequestDto();
    dto.setPage(1);
    dto.setSize(10);
    dto.setSort(List.of());

    Pageable pageable = dto.toPageable();

    assertThat(pageable.getSort().isSorted()).isFalse();
  }

  @Test
  void toPageable_shouldHandleNullSort() {
    PageRequestDto dto = new PageRequestDto();
    dto.setPage(1);
    dto.setSize(10);
    dto.setSort(null);

    Pageable pageable = dto.toPageable();

    assertThat(pageable.getSort().isSorted()).isFalse();
  }

  @Test
  void toPageable_shouldDefaultToPageZeroWhenPageIsNullOrZero() {
    PageRequestDto dto = new PageRequestDto();
    dto.setPage(0);
    dto.setSize(-1);
    dto.setSort(null);

    Pageable pageable = dto.toPageable();

    assertThat(pageable.getPageNumber()).isEqualTo(0);
    assertThat(pageable.getPageSize()).isEqualTo(20);
  }

  @Test
  void parseSorts_shouldIgnoreMalformedInput() {
    PageRequestDto dto = new PageRequestDto();
    dto.setSort(List.of("invalidFormat", "title,asc"));

    List<Sort.Order> orders = dto.toPageable().getSort().toList();

    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getProperty()).isEqualTo("title");
    assertThat(orders.get(0).getDirection()).isEqualTo(Sort.Direction.ASC);
  }

}