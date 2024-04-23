package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.dto.MyPageProfileDTO;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public User register(@RequestBody UserRegisterDTO userRegisterDTO) {
    User user = userService.register(userRegisterDTO);
    return user;
  }

  @GetMapping("/api/mypage/profile")
  public ResponseEntity<MyPageProfileDTO> readMyPage() {
    return ResponseEntity.ok(userService.readMyPage());
  }

  @PostMapping("/api/mypage/profile")
  public ResponseEntity<MyPageProfileDTO> updateMyPage(@RequestBody @Validated MyPageProfileDTO myPageProfileDTO) {
    return ResponseEntity.ok(userService.updateMyPage(myPageProfileDTO));
  }

  @GetMapping("/api/mypage/sale")
  public ResponseEntity<SaleListDTO> findAllSales(
      @RequestParam Integer size, @RequestParam(defaultValue = "0") Integer page
  ) {
    return ResponseEntity.ok(userService.findAllSales(size, page));
  }
}