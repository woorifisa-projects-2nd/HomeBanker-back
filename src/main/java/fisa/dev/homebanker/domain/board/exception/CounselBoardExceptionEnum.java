package fisa.dev.homebanker.domain.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CounselBoardExceptionEnum {
  C001("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  C002("페이지는 0부터 시작합니다.", HttpStatus.BAD_REQUEST),
  C003("페이지 크기는 1이상이여야 합니다.", HttpStatus.BAD_REQUEST);
  private final String msg;
  private final HttpStatus status;

}
