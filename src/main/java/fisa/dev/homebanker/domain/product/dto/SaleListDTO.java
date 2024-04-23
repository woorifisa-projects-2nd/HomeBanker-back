package fisa.dev.homebanker.domain.product.dto;

import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleListDTO {
  private PaginationDTO pagination;
  private List<SaleDTO> saleItems;
}
