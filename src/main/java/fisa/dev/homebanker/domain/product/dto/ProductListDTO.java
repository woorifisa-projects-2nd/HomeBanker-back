package fisa.dev.homebanker.domain.product.dto;

import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {

  private PaginationDTO pagination;
  private List<ProductDTO> productItems;
}
