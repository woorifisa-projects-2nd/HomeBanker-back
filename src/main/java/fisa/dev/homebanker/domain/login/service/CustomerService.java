package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.CustomerRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder bCryptPasswordEncoder;

  public Customer register(CustomerRegisterDTO customerRegisterDTO) {
    String customerName = customerRegisterDTO.getCustomerName();
    Date customerBirth = customerRegisterDTO.getCustomerBirth();
    String customerPhone = customerRegisterDTO.getCustomerPhone();
    String customerAddress = customerRegisterDTO.getCustomerAddress();
    String customerLoginId = customerRegisterDTO.getCustomerLoginId();
    String customerLoginPw = customerRegisterDTO.getCustomerLoginPw();
    String customerRole = customerRegisterDTO.getCustomerRole();
    String customerIdentificationNum = customerRegisterDTO.getCustomerIdentificationNum();
    Date joinDate = customerRegisterDTO.getJoinDate();

    Customer customer = new Customer();
    customer.setCustomerName(customerName);
    customer.setCustomerBirth(customerBirth);
    customer.setCustomerPhone(customerPhone);
    customer.setCustomerAddress(customerAddress);
    customer.setCustomerLoginId(customerLoginId);
    customer.setCustomerLoginPw(bCryptPasswordEncoder.encode(customerLoginPw));
    customer.setCustomerRole(customerRole);
    customer.setCustomerIdentificationNum(customerIdentificationNum);
    customer.setCustomerRecentLogin(null);
    customer.setJoinDate(joinDate);
    customerRepository.save(customer);
    return customer;
  }
}