package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.dto.MyPageDTO;
import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/api/mypage/profile/{id}")
  public ResponseEntity<MyPageDTO> readMyPage(@PathVariable Integer id) {
    return ResponseEntity.ok(userService.readMyPage(id));
  }

  @PostMapping("/api/mypage/profile/{id}")
  public ResponseEntity<MyPageDTO> updateMyPage(@PathVariable Integer id, @RequestBody MyPageDTO myPageDTO) {
    return ResponseEntity.ok(userService.updateMyPage(id, myPageDTO));
  }
}