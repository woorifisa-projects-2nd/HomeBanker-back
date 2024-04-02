package fisa.dev.homebanker.domain.login.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeId;

  @Column(nullable = false)
  private String employeeName;

  @Column(nullable = false)
  private String employeeDepartment;

  @Column(nullable = false)
  private String employeeGrade;

  @Column(nullable = false)
  private String employeeLoginId;

  @Column(nullable = false)
  private String employeeLoginPw;

  @Column(nullable = false)
  private String employeeRole;

  @Column(nullable = false)
  private LocalDateTime recentLogin;

}