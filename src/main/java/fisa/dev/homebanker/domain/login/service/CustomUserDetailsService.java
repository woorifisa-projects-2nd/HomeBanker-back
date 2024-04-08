package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    Customer customer = customerRepository.findByCustomerLoginId(loginId);

    if (customer != null) {
      System.out.println("customerLoginId가 동일한 사람 있음");
      return new CustomUserDetails(customer);
    }
    System.out.println("아이디가 다름");
    return null;
  }
}
