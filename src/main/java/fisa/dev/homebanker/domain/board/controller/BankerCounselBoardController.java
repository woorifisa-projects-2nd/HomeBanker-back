package fisa.dev.homebanker.domain.board.controller;

import fisa.dev.homebanker.domain.board.service.BankerCounselBoardService;
import lombok.RequiredArgsConstructor;
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

  @DeleteMapping("/{boardId}")
  public void deleteBoard(@PathVariable Long boardId) {
    bankerCounselBoardService.deleteBoard(boardId);
  }

  @PutMapping("/{boardId}")
  public void updateReply(@PathVariable Long boardId) {
    bankerCounselBoardService.updateReply(boardId);
  }

}
