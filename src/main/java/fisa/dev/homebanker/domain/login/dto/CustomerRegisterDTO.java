package fisa.dev.homebanker.domain.login.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterDTO {

  private String customerName;
  private Date customerBirth;
  private String customerPhone;
  private String customerAddress;
  private String customerLoginId;
  private String customerLoginPw;
  private String customerRole;
  private String customerIdentificationNum;
  private LocalDateTime customerRecentLogin;
  private Date joinDate;
}
