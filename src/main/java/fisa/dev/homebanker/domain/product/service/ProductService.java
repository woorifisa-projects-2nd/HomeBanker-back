package fisa.dev.homebanker.domain.product.service;

import fisa.dev.homebanker.domain.product.dto.ChangeVisibilityDTO;
import fisa.dev.homebanker.domain.product.dto.ProductDTO;
import fisa.dev.homebanker.domain.product.dto.ProductListDTO;
import fisa.dev.homebanker.domain.product.entity.Product;
import fisa.dev.homebanker.domain.product.entity.ProductType;
import fisa.dev.homebanker.domain.product.exception.ProductException;
import fisa.dev.homebanker.domain.product.exception.ProductionExceptionEnum;
import fisa.dev.homebanker.domain.product.repository.ProductRepository;
import fisa.dev.homebanker.global.util.pagination.PaginationResMaker;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

  private final ProductRepository productRepository;
  private final PaginationResMaker paginationResMaker;

  public ProductListDTO findAllProducts(String category, Integer size, Integer page) {
    if (page < 0) {
      throw new ProductException(ProductionExceptionEnum.POO2);
    }
    if (size <= 0) {
      throw new ProductException(ProductionExceptionEnum.P004);
    }
    ProductListDTO productListDTO = new ProductListDTO();

    Pageable pageable = PageRequest.of(page, size);
    Page<Product> foundPage;
    if (category.equals("all")) {
      foundPage = productRepository.findAll(pageable);
    } else {
      String typeName = findTypeName(category);
      foundPage = productRepository.findByProductCodeTypeName(typeName, pageable);
    }
    productListDTO.setPagination(paginationResMaker.makePaginationDto(foundPage)); //페이지네이션 세팅

    List<ProductDTO> productItems = foundPage.get()
        .map(product -> product.toDto())
        .toList();
    productListDTO.setProductItems(productItems);

    return productListDTO;
  }

  public String findTypeName(String category) {
    Optional<ProductType> optional = Arrays.stream(ProductType.values())
        .filter(c -> c.getEnglish().equals(category))
        .findAny();
    if (optional.isEmpty()) {
      throw new ProductException(ProductionExceptionEnum.P003);
    }
    String typeName = optional.get().getKorean();
    return typeName;
  }

  public ChangeVisibilityDTO changeVisibility(ChangeVisibilityDTO dto) {
    Long productId = dto.getProductId();
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      Product foundProduct = optionalProduct.get();
      if (dto.getIsShown() == true) {
        foundProduct.setIsShown(false);
      } else {
        foundProduct.setIsShown(true);
      }

      return ChangeVisibilityDTO.builder()
          .productId(foundProduct.getProductId())
          .isShown(foundProduct.getIsShown())
          .build();
    } else {
      throw new ProductException(ProductionExceptionEnum.P001);
    }
  }


}
