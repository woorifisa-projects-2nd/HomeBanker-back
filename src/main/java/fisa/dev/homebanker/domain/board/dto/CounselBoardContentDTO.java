package fisa.dev.homebanker.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CounselBoardContentDTO {

  @NotNull(message = "content(내용)는 필수 입력값입니다.")
  @Length(max = 500, message = "최대 글자수(500자)를 초과하였습니다.")
  private String content;
}
