package fisa.dev.homebanker.domain.board.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.board.service.BankerCounselBoardService;
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
@DisplayName("문의 게시판 테스트 (은행원)")
@AutoConfigureMockMvc
public class BankerCounselBoardControllerTest {

  @MockBean
  BankerCounselBoardService bankerCounselBoardService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  private String BANKER_LOGIN_ID = "admin";
  private String BANKER_LOGIN_PASSWORD = "pw";
  private String token;

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
  @DisplayName("회신 여부 업데이트-성공")
  public void writeBoardSuccess() throws Exception {

    mockMvc.perform(put("/api/banker/board/{boardId}", 1L)
            .header("Authorization", "Bearer " + token)//jwt 담기
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(bankerCounselBoardService).updateReply(anyLong(), anyString());
  }

  @Test
  @DisplayName("문의 게시글 삭제-성공")
  public void deleteBoardSuccess() throws Exception {
    mockMvc.perform(delete("/api/banker/board/{boardId}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)) //jwt 담기
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(bankerCounselBoardService).deleteBoard(anyLong());
  }


}
