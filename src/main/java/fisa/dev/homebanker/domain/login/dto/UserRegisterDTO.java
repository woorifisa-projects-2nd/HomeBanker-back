package fisa.dev.homebanker.domain.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

  @NotBlank(message = "필수 항목입니다.")
  private String name;

  @NotNull(message = "필수 항목입니다.")
  private Date birth;

  @NotBlank(message = "필수 항목입니다.")
  @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "지정된 양식을 지켜주세요. [예시] XXX-XXXX-XXXX")
  private String phone;

  @NotBlank(message = "필수 항목입니다.")
  private String address;

  @NotBlank(message = "필수 항목입니다.")
  private String loginId;

  private String loginPw;

  @NotBlank(message = "필수 항목입니다.")
  private String role;

  @NotBlank(message = "필수 항목입니다.")
  private String identificationNum;

  private LocalDateTime recentLogin;

  @NotNull(message = "필수 항목입니다.")
  private Date joinDate;
}
