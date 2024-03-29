package fisa.dev.homebanker.domain.board.controller;

import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.service.CounselBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CounselBoardController {

  private final CounselBoardService counselBoardService;

  /**
   * 상담 게시판 글 전체 조회
   */
  @GetMapping("")
  public ResponseEntity<CounselBoardListDTO> readAllCounselBoards(@RequestParam Integer size,
      @RequestParam Integer page) {
    CounselBoardListDTO response = counselBoardService.readAllCounselBoards(size, page);
    return ResponseEntity.ok(response);
  }

  /**
   * 상담 게시판 글 상세 조회
   */
  @GetMapping("/{boardId}")
  public ResponseEntity<CounselBoardDTO> readCounselBoard(@PathVariable Long boardId) {
    CounselBoardDTO response = counselBoardService.readCounselBoard(boardId);
    return ResponseEntity.ok(response);
  }

  /**
   * 상담 게시판 글 작성
   */
  @PostMapping("")
  public void addCounselBoard(@RequestBody CounselBoardContentDTO contentDTO) {
    counselBoardService.addCounselBoard(contentDTO);
  }

}
