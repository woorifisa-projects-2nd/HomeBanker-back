package fisa.dev.homebanker.domain.product.controller;

import fisa.dev.homebanker.domain.product.dto.ChangeVisibilityDTO;
import fisa.dev.homebanker.domain.product.dto.ProductListDTO;
import fisa.dev.homebanker.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banker/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;


  /**
   * 상품 전체 조회
   *
   * @param category 상품 카테고리
   * @param size     조회할 페이지 크기
   * @param page     조회할 페이지
   * @return 상품 리스트
   */
  @GetMapping("")
  public ResponseEntity<ProductListDTO> findAllProducts(
      @RequestParam(defaultValue = "all") String category,
      @RequestParam Integer size, @RequestParam(defaultValue = "0") Integer page) {
    return ResponseEntity.ok(productService.findAllProducts(category, size, page));
  }

  /**
   * 상품의 노출 여부 변경
   *
   * @param dto (상품 id, 상품의 현재 노출 상태)
   * @return (상품 id, 상품의 변경된 노출 상태)
   */
  @PostMapping("/show")
  public ResponseEntity<ChangeVisibilityDTO> changeVisible(
      @RequestBody @Validated ChangeVisibilityDTO dto) {
    return ResponseEntity.ok(productService.changeVisibility(dto));
  }


}
