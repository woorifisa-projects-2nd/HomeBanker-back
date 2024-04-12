package fisa.dev.homebanker.domain.board.dto;


import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CounselBoardListDTO {

  private PaginationDTO pagination;
  private List<CounselBoardDTO> boardItems;

}
