package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByCustomerLoginId(String loginId);
}