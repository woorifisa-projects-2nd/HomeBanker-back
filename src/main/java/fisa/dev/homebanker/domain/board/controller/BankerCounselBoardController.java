package fisa.dev.homebanker.domain.board.controller;

import fisa.dev.homebanker.domain.board.service.BankerCounselBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banker/board")
public class BankerCounselBoardController {

  private final BankerCounselBoardService bankerCounselBoardService;

  /**
   * 상담 게시글 삭제
   *
   * @param boardId 게시글 id
   */
  @DeleteMapping("/{boardId}")
  public void deleteBoard(@PathVariable Long boardId) {
    bankerCounselBoardService.deleteBoard(boardId);
  }

  /**
   * 유선 회신 여부 업데이트
   *
   * @param boardId 게시글 id
   */
  @PutMapping("/{boardId}")
  public void updateReply(@PathVariable Long boardId) {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    bankerCounselBoardService.updateReply(boardId, loginId);
  }

}
