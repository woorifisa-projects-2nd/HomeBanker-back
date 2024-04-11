package fisa.dev.homebanker.domain.login.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

  private String name;
  private Date birth;
  private String phone;
  private String address;
  private String loginId;
  private String loginPw;
  private String role;
  private String identificationNum;
  private LocalDateTime recentLogin;
  private Date joinDate;
}
