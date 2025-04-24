package com.example.gifticon_trader.gifticon.in.rest.dto;


import com.example.gifticon_trader.common.dto.PageRequestDto;
import com.example.gifticon_trader.gifticon.domain.GifticonStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GifticonSearchDto extends PageRequestDto {

  @Schema(description = "검색 키워드")
  private String keyword;
  @Schema(description = "상태")
  private List<GifticonStatus> statuses;
  @Schema(description = "만료일 검색 시작일")
  private LocalDate startDate;
  @Schema(description = "만료일 검색 종료일")
  private LocalDate endDate;
  @Schema(description = "판매자 ID")
  private Long sellerId;

  @Override
  public Pageable toPageable() {
    if (getSort() == null || getSort().isEmpty()) {
      setSort(List.of("createdDate,desc")); // 기본 정렬
    }
    return super.toPageable();
  }
}
