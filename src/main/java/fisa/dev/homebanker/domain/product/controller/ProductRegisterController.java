package fisa.dev.homebanker.domain.product.controller;

import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.entity.Sale;
import fisa.dev.homebanker.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRegisterController {
  private final ProductService productService;

  @PostMapping("/register")
  public ResponseEntity<SaleDTO> registerProduct(@RequestBody @Validated SaleDTO dto) {
    return ResponseEntity.ok(productService.registerProduct(dto));
  }
}
