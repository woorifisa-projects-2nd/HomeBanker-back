package fisa.dev.homebanker.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ProductCode {

  @Id
  private String productCode;

  @Column(nullable = false, length = 30, name = "type_name")
  private String typeName;
}
