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
@Table(name = "customer_log")
public class CustomerLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long logId;

  @Column(name = "customer_id", updatable = false)
  private String customerId;

  @Column(name = "customer_status", updatable = false)
  private String customerStatus;

  @Column(name = "customer_time", updatable = false)
  private LocalDateTime customerTime;
}