package fisa.dev.homebanker.domain.login.exception;

import fisa.dev.homebanker.domain.product.exception.ProductionExceptionEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class UserException extends RuntimeException {
  private String msg;
  private HttpStatus status;

  public UserException(UserExceptionEnum e) {
    this.msg = e.getMsg();
    this.status = e.getStatus();
  }
}
