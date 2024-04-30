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
import java.time.LocalDate;
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
  @JoinColumn(name = "customer_login_id")
  private User customerLoginId;

  @ManyToOne
  @JoinColumn(name = "banker_login_id")
  private User bankerLoginId;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product productId;

  @Column
  private LocalDate createdAt;

  @Column
  private Integer saleMonth;

  @Column
  private Integer saleAmount;

  public SaleDTO toDto() {
    return SaleDTO.builder()
        .saleId(saleId)
        .customerLoginId(customerLoginId.getLoginId())
        .bankerLoginId(bankerLoginId.getLoginId())
        .productId(productId.getProductId())
        .productName(productId.getProductName())
        .productDescription(productId.getProductDescription())
        .saleMonth(saleMonth)
        .saleAmount(saleAmount)
        .createdAt(String.valueOf(createdAt))
        .build();
  }
}
