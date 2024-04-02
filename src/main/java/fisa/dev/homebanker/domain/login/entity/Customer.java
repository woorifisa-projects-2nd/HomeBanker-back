package fisa.dev.homebanker.domain.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int customerId;

  @Column(nullable = false)
  private String customerName;

  @Column(nullable = false)
  private Date customerBirth;

  @Column(nullable = false)
  private String customerPhone;

  @Column(nullable = false)
  private String customerAddress;

  @Column(nullable = false)
  private String customerLoginId;

  @Column(nullable = false)
  private String customerLoginPw;

  @Column(nullable = false)
  private String customerIdentificationNum;

  @Column(nullable = false)
  private LocalDateTime customerRecentLogin;

  @Column(nullable = false)
  private Date joinDate;
}