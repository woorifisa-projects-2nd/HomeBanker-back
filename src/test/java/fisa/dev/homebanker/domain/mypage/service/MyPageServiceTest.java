package fisa.dev.homebanker.domain.mypage.service;

import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.domain.mypage.dto.MyPageProfileDTO;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DisplayName("마이페이지 테스트")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
class MyPageServiceTest {

  @Autowired
  MyPageService myPageService;

  @Autowired
  UserRepository userRepository;

  private final String loginId = "customer";
  private final String loginPw = "pw";

  @Autowired
  AuthenticationManager authenticationManager;

  @BeforeEach
  public void login() {
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId,
        loginPw, null);
    Authentication authenticate = authenticationManager.authenticate(authToken);

    SecurityContextHolder.setContext(new SecurityContextImpl(authenticate));
  }


  @Test
  @DisplayName("프로필 조회")
  void readMyPage() {
    MyPageProfileDTO myPageProfileDTO = myPageService.readMyPage();
    Assertions.assertEquals(myPageProfileDTO.getAddress(), "서울");
    Assertions.assertEquals(myPageProfileDTO.getJoinDate(), LocalDate.of(2024, 04, 16));

  }

  @Test
  @DisplayName("프로필 업데이트")
  void updateMyPage() {
    final String newAddress = "부산";
    final String newPhone = "01011112222";

    MyPageProfileDTO updateDTO = MyPageProfileDTO.builder()
        .name("조유정") //변경 불가 기존값
        .address(newAddress)
        .phone(newPhone)
        .joinDate(LocalDate.of(2024, 04, 16)) //변경 불가 기존값
        .build();

    MyPageProfileDTO myPageProfileDTO = myPageService.updateMyPage(updateDTO);

    Assertions.assertEquals(myPageProfileDTO.getAddress(), newAddress);
    Assertions.assertEquals(myPageProfileDTO.getPhone(), newPhone);

  }

  @Test
  @DisplayName("가입한 상품 조회")
  void findAllSales() {
    final int page = 0;
    final int size = 5;

    SaleListDTO sales = myPageService.findAllSales(size, page, loginId);
    Assertions.assertEquals(sales.getPagination().getPageNumber(), page);
    Assertions.assertTrue(sales.getSaleItems().size() <= size);
    
  }
}