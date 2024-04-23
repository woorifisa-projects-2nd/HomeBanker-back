package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.CustomerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLogRepository extends JpaRepository<CustomerLog, Integer> {

}
