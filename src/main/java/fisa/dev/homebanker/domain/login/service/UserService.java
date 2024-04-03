package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.LoginRequestDTO;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  // Spring Security를 사용한 로그인 구현 시 사용
  // private final BCryptPasswordEncoder encoder;

  /**
   * 로그인 기능 화면에서 LoginRequest(loginId, loginPw)을 입력받아 loginId와 loginPw 일치하면 User return loginId가
   * 존재하지 않거나 loginPw 일치하지 않으면 null return
   */
  public Customer login(LoginRequestDTO req) {
    Optional<Customer> optionalUser = userRepository.findByCustomerLoginId(req.getLoginId());
    // loginId와 일치하는 User가 없으면 null return
    if (optionalUser.isEmpty()) {
      return null;
    }

    Customer user = optionalUser.get();

    // 찾아온 User의 loginPw와 입력된 loginPw가 다르면 null return
    if (!user.getCustomerLoginPw().equals(req.getLoginPw())) {
      return null;
    }

    return user;
  }

  /**
   * loginId(String)를 입력받아 User을 return 해주는 기능 (인증, 인가) loginId가 null이거나(로그인 X) userId로 찾아온 User가
   * 없으면 null return loginId로 찾아온 User가 존재하면 User return
   */
  public Customer getLoginUserByLoginId(String loginId) {
    if (loginId == null) {
      return null;
    }

    Optional<Customer> optionalUser = userRepository.findByCustomerLoginId(loginId);
    if (optionalUser.isEmpty()) {
      return null;
    }

    return optionalUser.get();
  }
}