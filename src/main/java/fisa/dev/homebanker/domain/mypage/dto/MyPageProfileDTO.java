package fisa.dev.homebanker.domain.mypage.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyPageProfileDTO {

  @NotNull(message = "name(이름)은 필수 입력값입니다.")
  private String name;

  private LocalDate joinDate;

  @NotNull(message = "phone(전화번호)는 필수 입력값입니다.")
  private String phone;

  @NotNull(message = "address(주소)는 필수 입력값입니다.")
  private String address;
}
