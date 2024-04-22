package fisa.dev.homebanker.domain.login.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageDTO {
  @NotNull(message = "name(이름)은 필수 입력값입니다.")
  private String name;

  private Date joinDate;

  @NotNull(message = "phone(전화번호)는 필수 입력값입니다.")
  private String phone;

  @NotNull(message = "address(주소)는 필수 입력값입니다.")
  private String address;
}
