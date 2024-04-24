package fisa.dev.homebanker.domain.video;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.video.controller.OpenviduController;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@DisplayName("화상 상담 테스트")
@AutoConfigureMockMvc
public class VideoControllerTest {

  @MockBean
  OpenviduController openviduController;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;
  private String BANKER_LOGIN_ID = "admin";
  private String CUSTOMER_LOGIN_ID = "id";
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
  @DisplayName("세션 생성_성공")
  public void initializeSessionSuccess() throws Exception {

    Map<String, Object> map = new HashMap<>();
    String customSessionId = "sessionA";
    map.put("customSessionId", customSessionId);

    given(openviduController.initializeSession(map)).willReturn(ResponseEntity.ok(customSessionId));

    MvcResult result = mockMvc.perform(post("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .header("Authorization", "Bearer " + token)
            .content("{\n"
                + "    \"customSessionId\" : \"sessionA\"\n"
                + "}"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(customSessionId))
        .andDo(print())
        .andReturn();

    //then
    Assertions.assertEquals(customSessionId, result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("세션 생성_실패")
  public void initilaizeSessionFail()
      throws Exception {

    Map<String, Object> map = new HashMap<>();
    String customSessionId = "sessionA";
    map.put("customSessionId", customSessionId);

    given(openviduController.initializeSession(map)).willReturn(ResponseEntity.ok("full"));

    MvcResult result = mockMvc.perform(post("/api/sessions")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .header("Authorization", "Bearer " + token)
            .content("{\n"
                + "    \"customSessionId\" : \"sessionA\"\n"
                + "}"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string("full"))
        .andDo(print())
        .andReturn();

    //then
    Assertions.assertNotEquals(customSessionId, result.getResponse().getContentAsString());
    Assertions.assertEquals("full", result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("토큰 생성-성공")
  public void createTokenSuccess() throws Exception {
    //given
    String sessionId = "sessionA";
    String domain = "firstpenguin.shop";
    String expected = "wss://" + domain + "?sessionId=" + sessionId + "&token=";

    given(openviduController.createConnection(sessionId, new HashMap<>())).willReturn(
        ResponseEntity.ok(expected));

    //when
    MvcResult result = mockMvc.perform(post("/api/sessions/{sessionId}/connections", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .header("Authorization", "Bearer " + token))
        .andExpect(status().is2xxSuccessful())
        .andDo(print())
        .andReturn();

    //then
    Assertions.assertTrue(result.getResponse().getContentAsString().contains("wss://"));
    Assertions.assertTrue(result.getResponse().getContentAsString().contains(sessionId));
  }

  @Test
  @DisplayName("세션 파괴_성공")
  public void destroySessionSuccess() throws Exception {
    //when
    String sessionId = "sessionA";
    MvcResult result = mockMvc.perform(post("/api/sessions/{sessionId}/destroy", sessionId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .header("Authorization", "Bearer " + token))
        .andExpect(status().is2xxSuccessful())
        .andDo(print())
        .andReturn();
  }


}
