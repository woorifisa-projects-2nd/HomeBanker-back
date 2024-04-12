package fisa.dev.homebanker.domain.board.controller;

import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.service.CounselBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
   * 전체 상담 게시글 조회
   *
   * @param size 페이지 크기
   * @param page 페이지 번호
   * @return
   */
  @GetMapping("")
  public ResponseEntity<CounselBoardListDTO> readAllCounselBoards(@RequestParam Integer size,
      @RequestParam Integer page) {
    CounselBoardListDTO response = counselBoardService.readAllCounselBoards(page, size);
    return ResponseEntity.ok(response);
  }

  /**
   * 상담 게시글 상세 조회
   *
   * @param boardId 게시글 id
   * @return
   */
  @GetMapping("/{boardId}")
  public ResponseEntity<CounselBoardDTO> readCounselBoard(@PathVariable Long boardId) {
    CounselBoardDTO response = counselBoardService.readCounselBoard(boardId);
    return ResponseEntity.ok(response);
  }

  /**
   * 상담 게시글 작성
   *
   * @param contentDTO 게시글 내용
   */
  @PostMapping("")
  public void addCounselBoard(@RequestBody @Validated CounselBoardContentDTO contentDTO) {
    counselBoardService.addCounselBoard(contentDTO);
  }

}
