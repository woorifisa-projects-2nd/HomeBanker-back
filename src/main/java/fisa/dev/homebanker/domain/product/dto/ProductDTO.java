package fisa.dev.homebanker.domain.product.dto;

import fisa.dev.homebanker.domain.product.entity.ProductCode;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDTO {

  private Long productId;
  private String productName;
  private ProductCode productCode;
  private String productDescription;
  private BigDecimal productInterest;
  private Integer maxMonth;
  private Boolean isShown;

}
