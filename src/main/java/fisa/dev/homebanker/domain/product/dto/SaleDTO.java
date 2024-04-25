package fisa.dev.homebanker.domain.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaleDTO {
  private Long saleId;

  @NotNull(message = "customerLoginId는 필수 입력값입니다.")
//  private String customerLoginId;
  private String customer;

  @NotNull(message = "bankerLoginId는 필수 입력값입니다.")
//  private String bankerLoginId;
  private String banker;

  @NotNull(message = "productId는 필수 입력값입니다.")
  private Long productId;

  private String productName;

  private String productDescription;

  @NotNull(message = "salaMonth(가입 날짜)는 필수 입력값입니다.")
  @Max(value = 480, message = "saleMonth는 최대 480 이하여야 합니다.")
  private Integer saleMonth;

  @NotNull(message = "saleAmount(가입 개월수)는 필수 입력값입니다.")
  @Max(value = 2100000000, message = "최대 금액(21억)을 초과하였습니다.")
  private Integer saleAmount;

  private LocalDate createdAt;
}
