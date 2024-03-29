package fisa.dev.homebanker.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeVisibilityDTO {

  private Long productId;
  private Boolean isShown;

}
