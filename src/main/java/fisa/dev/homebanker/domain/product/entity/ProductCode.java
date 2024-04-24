package fisa.dev.homebanker.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCode {

  @Id
  private String productCode;

  @Column(nullable = false, length = 30, name = "type_name")
  private String typeName;
}
