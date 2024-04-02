package fisa.dev.homebanker.domain.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CounselBoardException extends RuntimeException {

  private String msg;
  private HttpStatus status;

  public CounselBoardException(CounselBoardExceptionEnum e) {
    this.msg = e.getMsg();
    this.status = e.getStatus();
  }
}
