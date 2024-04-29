package fisa.dev.homebanker.domain.mypage.service;

import fisa.dev.homebanker.domain.login.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("마이페이지 테스트")
@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

  @Autowired
  MyPageService myPageService;

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("프로필 조회")
  void readMyPage() {
  }

  @Test
  @DisplayName("프로필 업데이트")
  void updateMyPage() {
  }

  @Test
  @DisplayName("가입한 상품 조회")
  void findAllSales() {
  }
}