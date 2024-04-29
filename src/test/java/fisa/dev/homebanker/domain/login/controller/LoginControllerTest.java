package fisa.dev.homebanker.domain.login.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.login.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("로그인 테스트")
@ActiveProfiles("local")
public class LoginControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  @Autowired
  JwtUtil jwtUtil;
  private final String CUSTOMER_LOGIN_ID = "customer";
  private final String BANKER_LOGIN_ID = "banker";
  private final String LOGIN_PASSWORD = "pw";
  private final String LOGIN_WRONG_PASSWORD = "pww";
  private final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
  private final String ROLE_ADMIN = "ROLE_ADMIN";


  //mockMvc 객체 생성, Spring Security 환경 setup
  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .apply(springSecurity())
        .build();
  }

  @Test
  @DisplayName("고객 로그인-성공")
  public void customerLoginSuccess() throws Exception {
    //given

    //when
    MvcResult result = mockMvc.perform(post("/login")
            .contentType("application/json")
            .param("loginId", CUSTOMER_LOGIN_ID)
            .param("loginPw", LOGIN_PASSWORD))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"))
        .andDo(print())
        .andReturn();

    //then
    String header = result.getResponse().getHeader("Authorization");
    assertTrue(header.contains("Bearer"));
    String token = header.split(" ")[1];

    assertEquals(jwtUtil.getRole(token), ROLE_CUSTOMER);
    assertEquals(jwtUtil.getLoginId(token), CUSTOMER_LOGIN_ID);

  }

  @Test
  @DisplayName("은행원 로그인-성공")
  public void bankerLoginSuccess() throws Exception {
    //given

    //when
    MvcResult result = mockMvc.perform(post("/login")
            .contentType("application/json")
            .param("loginId", BANKER_LOGIN_ID)
            .param("loginPw", LOGIN_PASSWORD))
        .andExpect(status().isOk())
        .andExpect(header().exists("Authorization"))
        .andDo(print())
        .andReturn();

    //then
    String header = result.getResponse().getHeader("Authorization");
    assertTrue(header.contains("Bearer"));
    String token = header.split(" ")[1];

    assertEquals(jwtUtil.getRole(token), ROLE_ADMIN);
    assertEquals(jwtUtil.getLoginId(token), BANKER_LOGIN_ID);

  }

  @Test
  @DisplayName("로그인-실패")
  public void LoginFail() throws Exception {
    //given

    //when
    MvcResult result = mockMvc.perform(post("/login")
            .contentType("application/json")
            .param("loginId", CUSTOMER_LOGIN_ID)
            .param("loginPw", LOGIN_WRONG_PASSWORD))
        .andExpect(status().is4xxClientError())
        .andExpect(header().doesNotExist("Authorization"))
        .andDo(print())
        .andReturn();

    //then
    assertEquals(result.getResponse().getStatus(), 401);
  }


}
