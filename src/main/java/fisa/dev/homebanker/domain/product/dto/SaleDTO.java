package fisa.dev.homebanker.domain.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaleDTO {
  @NotNull(message = "customerId는 필수 입력값입니다.")
  private Integer customerId;

  @NotNull(message = "bankerId는 필수 입력값입니다.")
  private Integer bankerId;

  @NotNull(message = "productId는 필수 입력값입니다.")
  private Long productId;

  @NotNull(message = "salaMonth(가입 날짜)는 필수 입력값입니다.")
  @Max(value = 480, message = "saleMonth는 최대 480 이하여야 합니다.")
  private Integer saleMonth;

  @NotNull(message = "saleAmount(가입 개월수)는 필수 입력값입니다.")
  @Max(value = 2100000000, message = "최대 금액(100억)을 초과하였습니다.")
  private Integer saleAmount;
}
