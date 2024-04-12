package fisa.dev.homebanker.domain.product.entity;

import fisa.dev.homebanker.domain.product.dto.ProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @ManyToOne
  @JoinColumn(name = "product_code")
  private ProductCode productCode;

  @Column(nullable = false, length = 30)
  private String productName;

  @Column(nullable = false, length = 100)
  private String productDescription;

  private BigDecimal productInterest;

  private Integer maxMonth;

  @Column(nullable = false, name = "is_shown")
  private Boolean isShown;

  public ProductDTO toDto() {
    return ProductDTO.builder()
        .productId(productId)
        .productName(productName)
        .productCode(productCode)
        .productDescription(productDescription)
        .productInterest(productInterest)
        .maxMonth(maxMonth)
        .isShown(isShown)
        .build();
  }
}
