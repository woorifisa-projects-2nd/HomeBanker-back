package fisa.dev.homebanker.domain.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
@Table(name = "banker_log")
public class BankerLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long logId;

  @Column(name = "banker_id", updatable = false)
  private String bankerId;

  @Column(name = "banker_status", updatable = false)
  private String bankerStatus;

  @Column(name = "banker_time", updatable = false)
  private LocalDateTime bankerTime;
}