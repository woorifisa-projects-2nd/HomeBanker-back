package fisa.dev.homebanker.domain.login.controller;

import fisa.dev.homebanker.domain.login.dto.LoginRequestDTO;
import fisa.dev.homebanker.domain.login.entity.Employee;
import fisa.dev.homebanker.domain.login.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping("/login")
  public String login(@RequestBody LoginRequestDTO loginRequest) {
    Employee employee = employeeService.login(loginRequest);

    if (employee == null) {
      return "Fail";
    }

    return "Success";


  }
}