package fisa.dev.homebanker.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

  private String msg;
  private HttpStatus status;
}
