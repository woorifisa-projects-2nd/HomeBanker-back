package fisa.dev.homebanker.domain.log.service;

import fisa.dev.homebanker.domain.log.entity.BankerLog;
import fisa.dev.homebanker.domain.log.entity.CustomerLog;
import fisa.dev.homebanker.domain.login.repository.BankerLogRepository;
import fisa.dev.homebanker.domain.login.repository.CustomerLogRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LogService {

  private final CustomerLogRepository customerLogRepository;
  private final BankerLogRepository bankerLogRepository;

  public CustomerLog customerLoginService(String loginId) {
    LocalDateTime currentTime = LocalDateTime.now();

    return customerLogRepository.save(CustomerLog.builder()
        .customerId(loginId)
        .customerStatus("login")
        .customerTime(currentTime)
        .build());
  }

  public CustomerLog customerLogoutService(String loginId) {
    LocalDateTime currentTime = LocalDateTime.now();

    return customerLogRepository.save(CustomerLog.builder()
        .customerId(loginId)
        .customerStatus("logout")
        .customerTime(currentTime)
        .build());
  }

  public BankerLog bankerLoginService(String loginId) {
    LocalDateTime currentTime = LocalDateTime.now();

    return bankerLogRepository.save(BankerLog.builder()
        .bankerId(loginId)
        .bankerStatus("login")
        .bankerTime(currentTime)
        .build());
  }

  public BankerLog bankerLogoutService(String loginId) {
    LocalDateTime currentTime = LocalDateTime.now();

    return bankerLogRepository.save(BankerLog.builder()
        .bankerId(loginId)
        .bankerStatus("logout")
        .bankerTime(currentTime)
        .build());
  }

}