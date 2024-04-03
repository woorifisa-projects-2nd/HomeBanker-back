package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.LoginRequestDTO;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  /**
   * 로그인 기능 화면에서 LoginRequest(loginId, loginPw)을 입력받아 loginId와 loginPw 일치하면 Customer return loginId가
   * 존재하지 않거나 loginPw 일치하지 않으면 null return
   */
  public Customer login(LoginRequestDTO req) {
    Optional<Customer> customer = customerRepository.findByCustomerLoginId(req.getLoginId());
    // loginId와 일치하는 Customer가 없으면 null return
    if (customer.isEmpty()) {
      return null;
    }

    Customer user = customer.get();

    // 찾아온 User의 loginPw와 입력된 loginPw가 다르면 null return
    if (!user.getCustomerLoginPw().equals(req.getLoginPw())) {
      return null;
    }
    return user;

  }
}