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
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "customer_log")
public class CustomerLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long logId;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "customer_status")
  private String customerStatus;

  @Column(name = "customer_time")
  private LocalDateTime customerTime;
}