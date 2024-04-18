package fisa.dev.homebanker.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaleDTO {
  private Long customerId;
  private Long bankerId;
  private Long productId;
  private Integer saleMonth;
  private Integer saleAmount;
}
