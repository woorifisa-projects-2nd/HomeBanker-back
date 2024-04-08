package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.dto.CustomerRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.Customer;
import fisa.dev.homebanker.domain.login.jwt.JwtUtil;
import fisa.dev.homebanker.domain.login.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api")
public class CustomerController {

  private final CustomerService customerService;
  private final JwtUtil jwtUtil;

//  @PostMapping("/login")
//  public String login(@RequestBody LoginRequestDTO loginRequest) {
//    Customer customer = customerService.login(loginRequest);
//
//    // 발급할 토큰 초기화
//    String token = "";
//
//    if (customer == null) {
//      return "Fail";
//    }
//
//    // 로그인 성공하면 토큰을 발급
//    token = jwtTokenUtil.generateCustomerToken(customer);
//
//    return token;
//  }

  @PostMapping("/register")
  public Customer register(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
    Customer customer = customerService.register(customerRegisterDTO);
    return customer;
  }

  @GetMapping("/admin")
  public String adminPage() {
    return "admin page";
  }
}