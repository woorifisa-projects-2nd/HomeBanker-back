package fisa.dev.homebanker.domain.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

  private String loginId;
  private String loginPw;

}