package fisa.dev.homebanker.domain.mypage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.mypage.dto.MyPageProfileDTO;
import fisa.dev.homebanker.domain.mypage.service.MyPageService;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@DisplayName("마이페이지 프로필 테스트")
@AutoConfigureMockMvc
public class MyPageControllerTest {

  @MockBean
  MyPageService myPageService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  private String CUSTOMER_LOGIN_ID = "id";
  private String CUSTOMER_LOGIN_PASSWORD = "pw";
  private String token;

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
  @DisplayName("프로필 조회")
  public void readMyProfile() throws Exception {
    //given
    MyPageProfileDTO myPageDTO = MyPageProfileDTO.builder()
        .name("김경은")
        .phone("01011112222")
        .address("서울시")
        .joinDate(LocalDate.of(2024, 04, 20))
        .build();

    given(myPageService.readMyPage())
        .willReturn(myPageDTO);

    //when
    MvcResult result = mockMvc.perform(
            get("/api/mypage/profile")
                .header("Authorization", "Bearer " + token)//jwt 담기
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.phone").exists())
        .andExpect(jsonPath("$.address").exists())
        .andExpect(jsonPath("$.joinDate").exists())
        .andReturn();

    //then
    verify(myPageService).readMyPage();
  }

  @Test
  @DisplayName("프로필 업데이트-성공")
  public void updateMyProfileSuccess() throws Exception {

    //given
    MyPageProfileDTO myPageDTO = MyPageProfileDTO.builder()
        .name("김경은")
        .phone("01022223333")
        .address("인천시")
        .joinDate(LocalDate.of(2024, 04, 23))
        .build();

    given(myPageService.updateMyPage(myPageDTO))
        .willReturn(myPageDTO);

    mockMvc.perform(post("/api/mypage/profile")
            .header("Authorization", "Bearer " + token)//jwt 담기
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content("{\n"
                + "    \"name\" : \"김경은\",\n"
                + "    \"phone\" : \"01033334444\",\n"
                + "    \"address\" : \"인천시\", \n"
                + "    \"joinDate\" : \"2024-04-23\"\n"
                + "}"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(myPageService).updateMyPage(any(MyPageProfileDTO.class));
  }

  @Test
  @DisplayName("가입한 금융상품 조회")
  public void readMySales() throws Exception {
    //given
    SaleDTO saleDTO = SaleDTO.builder()
        .saleAmount(10000000)
        .saleMonth(24)
        .saleId(1L)
        .productId(7L)
        .productName("예금")
        .productDescription("예금상품입니다")
        .customerId(1)
        .bankerId(2)
        .createdAt(LocalDate.of(2024, 4, 20))
        .build();

    PaginationDTO paginationDTO = PaginationDTO.builder()
        .pageNumber(0)
        .totalPages(4)
        .totalElements(10L)
        .build();

    SaleListDTO saleListDTO = new SaleListDTO();

    List<SaleDTO> saleItems = new ArrayList<>();
    saleItems.add(saleDTO);

    saleListDTO.setSaleItems(saleItems);
    saleListDTO.setPagination(paginationDTO);

    given(myPageService.findAllSales(anyInt(), anyInt()))
        .willReturn(saleListDTO);

    //when
    int page = 0;
    int size = 5;
    MvcResult result = mockMvc.perform(
            get("/api/mypage/sale?page=" + page + "&size=" + size)
                .header("Authorization", "Bearer " + token)//jwt 담기
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.pagination").exists())
        .andExpect(jsonPath("$.saleItems").isArray())
        .andExpect(jsonPath("$.saleItems[0].productId").exists())
        .andReturn();

    //then
    verify(myPageService).findAllSales(anyInt(), anyInt());
  }

}
