package fisa.dev.homebanker.domain.product.entity;

import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long saleId;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customerId;

  @ManyToOne
  @JoinColumn(name = "banker_id")
  private User bankerId;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;

  @Column
  private LocalDateTime createdAt;

  @Column
  private Integer saleMonth;

  @Column
  private Integer saleAmount;

  public SaleDTO toDto() {
    return SaleDTO.builder()
        .saleId(saleId)
        .customerId(customerId.getId())
        .bankerId(bankerId.getId())
        .productId(productId.getProductId())
        .productName(productId.getProductName())
        .productDescription(productId.getProductDescription())
        .saleMonth(saleMonth)
        .saleAmount(saleAmount)
        .createdAt(createdAt)
        .build();
  }
}
