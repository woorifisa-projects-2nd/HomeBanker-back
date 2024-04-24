package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("문의 게시판 테스트 (은행원)")
@ExtendWith(MockitoExtension.class)
class BankerCounselBoardServiceTest {

  @Autowired
  BankerCounselBoardService bankerCounselBoardService;
  @Autowired
  CounselBoardRepository counselBoardRepository;

  private final String loginId = "admin";

  @Test
  @DisplayName("문의 게시글 삭제-성공")
  void deleteBoard() {
    long boardId = 1L;
    int prevSize = counselBoardRepository.findAll().size();
    bankerCounselBoardService.deleteBoard(boardId);
    int afterSize = counselBoardRepository.findAll().size();

    Assertions.assertTrue(counselBoardRepository.findById(boardId).isEmpty());
    Assertions.assertTrue(prevSize - 1 == afterSize);
  }

  @Test
  @DisplayName("회신 여부 업데이트-성공")
  void updateReply() {
    long boardId = 2L;
    CounselBoard prevUpdate = counselBoardRepository.findById(boardId).get();
    bankerCounselBoardService.updateReply(boardId, loginId);
    CounselBoard afterUpdate = counselBoardRepository.findById(boardId).get();

    Assertions.assertNull(prevUpdate.getUpdatedAt());
    Assertions.assertEquals(prevUpdate.getReply(), "N");
    Assertions.assertNotNull(afterUpdate.getUpdatedAt());
    Assertions.assertEquals(afterUpdate.getReply(), "Y");
    Assertions.assertNotNull(afterUpdate.getBankerName());
    Assertions.assertNotNull(afterUpdate.getBankerId());
  }
}