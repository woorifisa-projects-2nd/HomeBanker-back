package fisa.dev.homebanker.domain.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.product.dto.ChangeVisibilityDTO;
import fisa.dev.homebanker.domain.product.dto.ProductDTO;
import fisa.dev.homebanker.domain.product.dto.ProductListDTO;
import fisa.dev.homebanker.domain.product.entity.ProductCode;
import fisa.dev.homebanker.domain.product.service.ProductService;
import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@DisplayName("상품 관리 테스트")
@AutoConfigureMockMvc
public class ProductControllerTest {

  @MockBean
  ProductService productService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  private String token;
  private String BANKER_LOGIN_ID = "admin";
  private String BANKER_LOGIN_PASSWORD = "pw";

  public String login() throws Exception {
    MvcResult result = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .param("loginId", BANKER_LOGIN_ID)
            .param("loginPw", BANKER_LOGIN_PASSWORD))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    return result.getResponse().getHeader("Authorization");
  }

  @BeforeEach
  public void init() throws Exception {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(springSecurity())
        .build();

    token = login().split(" ")[1];
  }

  @Test
  @DisplayName("상품 노출여부 업데이트-성공")
  public void updateProductsVisibilitySuccess() throws Exception {

    mockMvc.perform(post("/api/banker/product/show")
            .header("Authorization", "Bearer " + token)//jwt 담기
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "    \"productId\" : 1,\n"
                + "    \"isShown\" : true\n"
                + "}"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(productService).changeVisibility(any(ChangeVisibilityDTO.class));
  }

  @Test
  @DisplayName("상품 전체 조회-성공")
  public void getAllProductsListSuccess() throws Exception {
    //given
    int page = 0;
    int size = 5;
    String category = "all";

    PaginationDTO paginationDTO = PaginationDTO.builder()
        .totalElements(30L)
        .pageNumber(6)
        .pageNumber(0)
        .build();

    ProductDTO productDTO = ProductDTO.builder()
        .productName("전체 상품")
        .productId(1L)
        .productDescription("전체 상품 조회 테스트")
        .productInterest(new BigDecimal(2.0))
        .isShown(true)
        .maxMonth(36)
        .productCode(new ProductCode("C001", "카드"))
        .build();

    ArrayList<ProductDTO> productDTOS = new ArrayList<>();
    productDTOS.add(productDTO);
    ProductListDTO productListDTO = new ProductListDTO(paginationDTO, productDTOS);

    given(productService.findAllProducts(category, size, page))
        .willReturn(productListDTO);

    //when
    MvcResult result = mockMvc.perform(
            get("/api/banker/product?category=" + category + "&page=" + page + "&size=" + size)
                .header("Authorization", "Bearer " + token)//jwt 담기
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.pagination").exists())
        .andExpect(jsonPath("$.productItems").isArray())
        .andExpect(jsonPath("$.productItems[0].productCode").exists())
        .andReturn();

    //then
    verify(productService).findAllProducts(category, size, page);
  }

  @Test
  @DisplayName("카드 상품 조회-성공")
  public void getCardProductsListSuccess() throws Exception {
    //given
    int page = 0;
    int size = 5;
    String category = "card";

    PaginationDTO paginationDTO = PaginationDTO.builder()
        .totalElements(30L)
        .pageNumber(6)
        .pageNumber(0)
        .build();

    ProductDTO productDTO = ProductDTO.builder()
        .productName("카드 상품")
        .productId(1L)
        .productDescription("카드 상품 조회 테스트")
        .productInterest(null)
        .isShown(true)
        .maxMonth(null)
        .productCode(new ProductCode("C001", "카드"))
        .build();

    ArrayList<ProductDTO> productDTOS = new ArrayList<>();
    productDTOS.add(productDTO);
    ProductListDTO productListDTO = new ProductListDTO(paginationDTO, productDTOS);

    given(productService.findAllProducts(category, size, page))
        .willReturn(productListDTO);

    //when
    MvcResult result = mockMvc.perform(
            get("/api/banker/product?category=" + category + "&page=" + page + "&size=" + size)
                .header("Authorization", "Bearer " + token)//jwt 담기
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.pagination").exists())
        .andExpect(jsonPath("$.productItems").isArray())
        .andExpect(jsonPath("$.productItems[0].productCode.typeName").value("카드"))
        .andReturn();

    //then
    verify(productService).findAllProducts(category, size, page);
  }

}
