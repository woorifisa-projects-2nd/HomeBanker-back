package fisa.dev.homebanker.domain.mypage.controller;

import fisa.dev.homebanker.domain.mypage.dto.MyPageProfileDTO;
import fisa.dev.homebanker.domain.mypage.service.MyPageService;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

  private final MyPageService myPageService;

  @GetMapping("/api/mypage/profile")
  public ResponseEntity<MyPageProfileDTO> readMyPage() {
    return ResponseEntity.ok(myPageService.readMyPage());
  }

  @PostMapping("/api/mypage/profile")
  public ResponseEntity<MyPageProfileDTO> updateMyPage(
      @RequestBody @Validated MyPageProfileDTO myPageProfileDTO) {
    return ResponseEntity.ok(myPageService.updateMyPage(myPageProfileDTO));
  }

  @GetMapping("/api/mypage/sale")
  public ResponseEntity<SaleListDTO> findAllSales(
      @RequestParam Integer size, @RequestParam(defaultValue = "0") Integer page
  ) {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(myPageService.findAllSales(size, page, loginId));
  }

}
