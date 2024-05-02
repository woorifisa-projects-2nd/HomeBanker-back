package fisa.dev.homebanker.domain.product.service;

import fisa.dev.homebanker.domain.product.dto.ChangeVisibilityDTO;
import fisa.dev.homebanker.domain.product.dto.ProductDTO;
import fisa.dev.homebanker.domain.product.dto.ProductListDTO;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.repository.ProductRepository;
import fisa.dev.homebanker.domain.product.repository.SaleRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
class ProductServiceTest {

  @Autowired
  ProductService productService;
  @Autowired
  ProductRepository productRepository;

  @Autowired
  SaleRepository saleRepository;

  @Test
  @DisplayName("상품 전체 조회")
  void findAllProducts() {
    String category = "all";
    int page = 0;
    int size = 3;
    ProductListDTO products = productService.findAllProducts(category, size, page);

    Assertions.assertEquals(products.getPagination().getPageNumber(), page);
    Assertions.assertEquals(products.getProductItems().size(), size);
    Assertions.assertEquals(products.getProductItems().get(0).getClass(), ProductDTO.class);
  }

  @Test
  @DisplayName("상품 노출여부 업데이트")
  void changeVisibility() {
    Long productId = 1L;
    boolean status = true;

    ChangeVisibilityDTO visibilityDTO = ChangeVisibilityDTO.builder()
        .productId(productId)
        .isShown(status)
        .build();

    Boolean prev = productRepository.findById(productId).get().getIsShown();
    productService.changeVisibility(visibilityDTO);
    Boolean after = productRepository.findById(productId).get().getIsShown();

    Assertions.assertEquals(status, prev);
    Assertions.assertNotEquals(prev, after);

  }

  @Test
  @DisplayName("상품 가입")
  void registerProduct() {

    SaleDTO saleDTO = SaleDTO.builder()
        .productId(1L)
        .bankerLoginId("banker")
        .customerLoginId("customer")
        .createdAt(LocalDate.now())
        .saleMonth(24)
        .saleAmount(10000000)
        .build();

    int prev = saleRepository.findAll().size();
    productService.registerProduct(saleDTO);
    int after = saleRepository.findAll().size();

    Assertions.assertEquals(prev + 1, after);

  }
}