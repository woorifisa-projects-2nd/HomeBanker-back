package fisa.dev.homebanker.domain.product.controller;

import com.amazonaws.Response;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.entity.Sale;
import fisa.dev.homebanker.domain.product.service.ProductService;
import fisa.dev.homebanker.domain.product.service.S3FileUploadService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRegisterController {

  private final ProductService productService;
  private final S3FileUploadService s3FileUploadService;

  @PostMapping("/register")
  public ResponseEntity<SaleDTO> registerProduct(@RequestBody @Validated SaleDTO dto) {
    return ResponseEntity.ok(productService.registerProduct(dto));
  }

  @PostMapping("register/img")
  public void uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
    s3FileUploadService.uploadFile(file);

  }
}