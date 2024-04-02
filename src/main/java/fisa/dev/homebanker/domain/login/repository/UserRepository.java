package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, Long> {

  boolean existsByCustomerLoginId(String loginId);

  boolean existsByCustomerName(String name);

  Optional<Customer> findByCustomerLoginId(String loginId);
}