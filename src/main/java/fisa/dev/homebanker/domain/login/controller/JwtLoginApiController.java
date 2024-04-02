package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.dto.LoginRequest;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JwtLoginApiController {

  private final UserService userService;

//  @Value("${spring.jwt.secret}")
//  private String secretKey;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest) {
    Customer user = userService.login(loginRequest);

    if (user == null) {
      return "Fail";
    }

    return "Success";
    // 로그인 성공 => Jwt Token 발급
//    long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
//
//    String jwtToken = JwtTokenUtil.createToken(user.getLoginId(), secretKey, expireTimeMs);
//
//    return jwtToken;
  }

//  @GetMapping("/info")
//  public String userInfo(Authentication auth) {
//    User loginUser = userService.getLoginUserByLoginId(auth.getName());
//
//    return String.format("loginId : %s\nnickname : %s\nrole : %s",
//        loginUser.getLoginId(), loginUser.getName(), loginUser.getRole().name());
//  }

  @GetMapping("/admin")
  public String adminPage() {
    return "Success";
  }
}