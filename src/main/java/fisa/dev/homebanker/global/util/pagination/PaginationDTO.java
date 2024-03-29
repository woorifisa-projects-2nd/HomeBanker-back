package fisa.dev.homebanker.global.util.pagination;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaginationDTO {

  private Long totalElements;
  private Integer totalPages;
  private Integer pageNumber;


}
