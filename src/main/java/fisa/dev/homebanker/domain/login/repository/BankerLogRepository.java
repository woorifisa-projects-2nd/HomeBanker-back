package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.BankerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankerLogRepository extends JpaRepository<BankerLog, Integer> {

}
