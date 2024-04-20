package fisa.dev.homebanker.domain.login.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageDTO {
  private String name;
  private Date joinDate;
  private String phone;
  private String address;
}
