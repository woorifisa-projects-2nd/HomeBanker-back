package fisa.dev.homebanker.domain.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.service.ProductService;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@DisplayName("상품 가입 테스트")
@AutoConfigureMockMvc
@ActiveProfiles("local")
class ProductRegisterControllerTest {

  @MockBean
  ProductService productService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  private String token;
  private String CUSTOMER_LOGIN_ID = "customer";
  private String BANKER_LOGIN_ID = "banker";
  private String CUSTOMER_LOGIN_PASSWORD = "pw";

  public String login() throws Exception {
    MvcResult result = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .param("loginId", CUSTOMER_LOGIN_ID)
            .param("loginPw", CUSTOMER_LOGIN_PASSWORD))
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
  @DisplayName("상품 가입")
  public void registerProductTest() throws Exception {
    mockMvc.perform(post("/api/product/register")
            .header("Authorization", "Bearer " + token)//jwt 담기
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content("{\n"
                + "    \"customerLoginId\" :\"" + CUSTOMER_LOGIN_ID + "\",\n"
                + "    \"bankerLoginId\" :\"" + BANKER_LOGIN_ID + "\",\n"
                + "    \"productId\" : 1,\n"
                + "    \"productName\" : \"예금상품\",\n"
                + "    \"productDescription\" : \"예금상품입니다\",\n"
                + "    \"saleMonth\" : 12,\n"
                + "    \"saleAmount\" : 10000000\n"
                + "}"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(productService).registerProduct(any(SaleDTO.class));
  }

}