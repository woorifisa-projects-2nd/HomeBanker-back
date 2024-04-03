package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmployeeLoginId(String loginId);
}