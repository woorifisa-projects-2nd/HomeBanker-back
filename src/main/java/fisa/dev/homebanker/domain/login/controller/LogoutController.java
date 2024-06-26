package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.log.service.LogService;
import fisa.dev.homebanker.domain.login.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogoutController {

  private final UserService userService;
  private final LogService logService;

  @PostMapping("/exit")
  public ResponseEntity<?> exit(@RequestBody Map<String, String> requestData) {
    String loginId = requestData.get("loginId");
    String role = requestData.get("role");

    if (SecurityContextHolder.getContext().getAuthentication().getName().equals(loginId)) {
      String authorizedRole = userService.findRoleByLoginId(loginId);
      if (authorizedRole.equals(role)) {

        log.info("로그아웃 = {} / {}", role, loginId);

        if (role.equals("ROLE_CUSTOMER")) {
          logService.customerLogoutService(loginId);
        } else if (role.equals("ROLE_ADMIN")) {
          logService.bankerLogoutService(loginId);
        }

      }
    }

    return ResponseEntity.ok().build();
  }
}