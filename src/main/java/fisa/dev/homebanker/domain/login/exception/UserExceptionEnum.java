package fisa.dev.homebanker.domain.login.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserExceptionEnum {
  P001("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  P002("해당 고객이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  P003("해당 은행원이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

  private final String msg;
  private final HttpStatus status;
}
