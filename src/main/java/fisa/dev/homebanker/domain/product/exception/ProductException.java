package fisa.dev.homebanker.domain.product.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends RuntimeException {

  private String msg;
  private HttpStatus status;

  public ProductException(ProductionExceptionEnum e) {
    this.msg = e.getMsg();
    this.status = e.getStatus();
  }

}
