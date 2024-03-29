package fisa.dev.homebanker.domain.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ProductionExceptionEnum {
  P001("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  POO2("페이지 번호는 0부터 시작합니다.", HttpStatus.NOT_ACCEPTABLE),
  P003("해당 카테고리는 존재하지 않습니다.", HttpStatus.NOT_FOUND);


  private final String msg;
  private final HttpStatus status;
}
