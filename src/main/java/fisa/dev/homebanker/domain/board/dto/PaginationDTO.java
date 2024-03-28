package fisa.dev.homebanker.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaginationDTO {

  private Long totalElements;
  private Integer totalPages;
  private Integer pageNumber;


}
