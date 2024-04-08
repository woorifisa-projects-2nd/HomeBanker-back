package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.entity.Employee;
import fisa.dev.homebanker.domain.login.repository.CustomerRepository;
import fisa.dev.homebanker.domain.login.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;
  private final EmployeeRepository employeeRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//    String customerRole = customerRepository.findByCustomerLoginId(loginId).getCustomerRole();
//    String employeeRole = employeeRepository.findByEmployeeLoginId(loginId).getEmployeeRole();
    Customer customer = customerRepository.findByCustomerLoginId(loginId);
    Employee employee = employeeRepository.findByEmployeeLoginId(loginId);

    if (customer != null) {
      System.out.println("customerLoginId가 동일한 사람 있음");
      return new CustomUserDetails(customer);
    } else if (employee != null) {
      System.out.println("employeeLoginId가 동일한 사람 있음");
//      return new EmployeeU;
    }
    System.out.println("아이디가 다름");
    return null;
  }
}
