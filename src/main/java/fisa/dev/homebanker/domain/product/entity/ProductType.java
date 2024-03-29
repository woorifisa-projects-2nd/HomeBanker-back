package fisa.dev.homebanker.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductType {

  CARD("card", "카드"),
  LOAN("loan", "대출"),
  DEPOSIT("deposit", "예금");

  private String english;
  private String korean;
}
