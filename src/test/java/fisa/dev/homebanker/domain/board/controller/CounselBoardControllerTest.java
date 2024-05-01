package fisa.dev.homebanker.domain.board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fisa.dev.homebanker.domain.board.dto.BankerDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.service.CounselBoardService;
import fisa.dev.homebanker.global.util.pagination.PaginationDTO;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@DisplayName("문의 게시판 테스트 (고객)")
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class CounselBoardControllerTest {

  @MockBean
  CounselBoardService counselBoardService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  private String token;
  private String CUSTOMER_LOGIN_ID = "customer";
  private String CUSTOMER_LOGIN_PASSWORD = "pw";

  public String login() throws Exception {
    MvcResult result = mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .param("loginId", CUSTOMER_LOGIN_ID)
            .param("loginPw", CUSTOMER_LOGIN_PASSWORD))
        .andExpect(status().isOk())
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
  @DisplayName("게시글 작성 성공")
  public void writeBoardSuccess() throws Exception {

    mockMvc.perform(post("/api/board")
            .header("Authorization", "Bearer " + token) //jwt 담기
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content("{\n"
                + "   \"content\": \"문의내용\"\n"
                + "}"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    verify(counselBoardService).addCounselBoard(any(CounselBoardContentDTO.class), anyString());


  }

  @Test
  @DisplayName("게시글 작성 실패-내용 없음")
  public void writeBoardFailContentNull() throws Exception {

//    mockMvc.perform(post("/api/board")
//            .header("Authorization", "Bearer " + token) //jwt 담기
//            .contentType(MediaType.APPLICATION_JSON)
//            .characterEncoding(StandardCharsets.UTF_8)
//            .content("{\n"
//                + "   \"content\": \"\"\n"
//                + "}"))
//        .andExpect(status().is4xxClientError())
//        .andDo(print());
//
//    verify(counselBoardService, never()).addCounselBoard(any(CounselBoardContentDTO.class),
//        anyString());
    //validation에서 걸리기 때문에 service로 가지 않음
  }

  @Test
  @DisplayName("게시글 작성 실패-500자 초과")
  public void writeBoardFailOverMax() throws Exception {

    mockMvc.perform(post("/api/board")
            .header("Authorization", "Bearer " + token) //jwt 담기
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
            .content("{\n"
                + "   \"content\": \"국정감사 및 조사에 관한 절차 기타 필요한 사항은 법률로 정한다. 비상계엄이 선포된 때에는 법률이 정하는 바에 의하여 영장제도, 언론·출판·집회·결사의 자유, 정부나 법원의 권한에 관하여 특별한 조치를 할 수 있다. 국회의원은 그 지위를 남용하여 국가·공공단체 또는 기업체와의 계약이나 그 처분에 의하여 재산상의 권리·이익 또는 직위를 취득하거나 타인을 위하여 그 취득을 알선할 수 없다. 정당은 그 목적·조직과 활동이 민주적이어야 하며, 국민의 정치적 의사형성에 참여하는데 필요한 조직을 가져야 한다. 모든 국민은 신속한 재판을 받을 권리를 가진다. 형사피고인은 상당한 이유가 없는 한 지체없이 공개재판을 받을 권리를 가진다.\n"
                + "\n"
                + "국가는 국민 모두의 생산 및 생활의 기반이 되는 국토의 효율적이고 균형있는 이용·개발과 보전을 위하여 법률이 정하는 바에 의하여 그에 관한 필요한 제한과 의무를 과할 수 있다. 법률이 정하는 주요방위산업체에 종사하는 근로자의 단체행동권은 법률이 정하는 바에 의하여 이를 제한하거나 인정하지 아니할 수 있다. 모든 국민은 법률이 정하는 바에 의하여 납세의 의무를 진다. 이 헌법시행 당시에 이 헌법에 의하여 새로 설치될 기관의 권한에 속하는 직무를 행하고 있는 기관은 이 헌법에 의하여 새로운 기관이 설치될 때까지 존속하며 그 직무를 행한다. 대통령은 법률이 정하는 바에 의하여 훈장 기타의 영전을 수여한다.\"\n"
                + "}"))
        .andExpect(status().is4xxClientError())
        .andDo(print());

    verify(counselBoardService, never()).addCounselBoard(any(CounselBoardContentDTO.class),
        anyString());
    //validation에서 걸리기 때문에 service로 가지 않음
  }

  @Test
  @DisplayName("작성한 게시글 전체 조회-성공")
  public void readAllBoardsSuccess() throws Exception {
    int size = 5;
    int page = 0;

    PaginationDTO paginationDTO = PaginationDTO.builder()
        .pageNumber(page)
        .totalPages(2)
        .totalElements(10L)
        .build();

    //given
    given(counselBoardService.readAllCounselBoards(page, size, "id"))
        .willReturn(CounselBoardListDTO.builder()
            .pagination(paginationDTO)
            .boardItems(new ArrayList<CounselBoardDTO>())
            .build());

    //when
    mockMvc.perform(get("/api/board?size=" + size + "&page=" + page)
            .header("Authorization", "Bearer " + token)) //jwt 담기
        .andExpect(status().is2xxSuccessful())
        //.andExpect(jsonPath("$.pagination").exists())
        //.andExpect(jsonPath("$.boardItems").isArray())
        .andDo(print());

    //then
    verify(counselBoardService).readAllCounselBoards(anyInt(), anyInt(), anyString());
  }

  @Test
  @DisplayName("게시글 상세 조회-성공")
  public void readBoardSuccess() throws Exception {
    //given
    given(counselBoardService.readCounselBoard(1L))
        .willReturn(CounselBoardDTO.builder()
            .boardId(1L)
            .customerId(1L)
            .content("문의내용")
            .createdAt(LocalDateTime.now())
            .customerName("김경은")
            .replyYN("N")
            .telephone("01011111111")
            .updatedAt(null)
            .banker(new BankerDTO(2, "은행원"))
            .build());

    //when
    mockMvc.perform(get("/api/board/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)) //jwt 담기
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.boardId").exists())
        .andExpect(jsonPath("$.customerName").exists())
        .andExpect(jsonPath("$.banker.bankerId").exists())
        .andDo(print());

    //then
    verify(counselBoardService).readCounselBoard(anyLong());
  }
}




