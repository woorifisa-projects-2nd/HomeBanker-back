package fisa.dev.homebanker.domain.product.dto;

import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDTO {

  private PaginationDTO pagination;
  private List<ProductDTO> productItems;

}
