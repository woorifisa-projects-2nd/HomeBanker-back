package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Customer findByCustomerLoginId(String loginId);
}