package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Employee findByEmployeeLoginId(String loginId);
}