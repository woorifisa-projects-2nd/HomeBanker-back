package fisa.dev.homebanker.domain.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeVisibilityDTO {

  @NotNull(message = "productId(상품 아이디)는 필수 입력값입니다.")
  private Long productId;

  @NotNull(message = "isShown(상품 노출 여부)은 필수 입력값입니다.")
  private Boolean isShown;

}
