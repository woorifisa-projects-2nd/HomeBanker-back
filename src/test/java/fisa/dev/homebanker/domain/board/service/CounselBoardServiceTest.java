package fisa.dev.homebanker.domain.board.service;


import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DisplayName("문의 게시판 테스트 (고객)")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
class CounselBoardServiceTest {

  @Autowired
  private CounselBoardService counselBoardService;

  @Autowired
  private CounselBoardRepository counselBoardRepository;

  @Autowired
  private UserRepository userRepository;

  private String loginId = "id";

  @Test
  @DisplayName("작성한 게시글 전체 조회-성공")
  void readAllCounselBoards() {
    int page = 0;
    int size = 5;

    CounselBoardListDTO boardListDTO = counselBoardService.readAllCounselBoards(page, size,
        loginId);
    Assertions.assertTrue(boardListDTO.getPagination().getPageNumber() == page);
    //Assertions.assertTrue(boardListDTO.getBoardItems().size() == 10);
    Assertions.assertTrue(boardListDTO.getBoardItems().get(0).getCustomerId() == 1);
  }

  @Test
  @DisplayName("작성한 게시글 상세 조회-성공")
  void readCounselBoard() {
    long boardId = 1L;
    CounselBoardDTO counselBoardDTO = counselBoardService.readCounselBoard(boardId);

    Assertions.assertTrue(counselBoardDTO.getBoardId() == boardId);
  }

  @Test
  @DisplayName("게시글 작성-성공")
  void addCounselBoard() {
    String content = "테스트";
    CounselBoardContentDTO contentDTO = new CounselBoardContentDTO(content);

    int prevSize = counselBoardRepository.findAll().size();
    counselBoardService.addCounselBoard(contentDTO, loginId);
    int afterSize = counselBoardRepository.findAll().size();

    Assertions.assertTrue(prevSize + 1 == afterSize);
    CounselBoard newBoard = counselBoardRepository.findById(Long.valueOf(afterSize)).get();
    Assertions.assertTrue(newBoard.getContent().equals(content));
    Assertions.assertEquals(newBoard.getCustomer().getLoginId(), loginId);
  }
}