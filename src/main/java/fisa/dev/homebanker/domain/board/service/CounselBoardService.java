package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.exception.CounselBoardException;
import fisa.dev.homebanker.domain.board.exception.CounselBoardExceptionEnum;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import fisa.dev.homebanker.global.util.pagination.PaginationResMaker;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselBoardService {

  private final CounselBoardRepository counselBoardRepository;
  private final PaginationResMaker paginationResMaker;

  public CounselBoardListDTO readAllCounselBoards(Integer page, Integer size) {
    if (page < 0) {
      throw new CounselBoardException(CounselBoardExceptionEnum.C002);
    }
    if (size <= 0) {
      throw new CounselBoardException(CounselBoardExceptionEnum.C003);
    }
    Pageable pageable = PageRequest.of(page, size);
    Page<CounselBoard> foundPage = counselBoardRepository.findAll(pageable);

    CounselBoardListDTO counselBoardListDTO = new CounselBoardListDTO();
    counselBoardListDTO.setPagination(paginationResMaker.makePaginationDto(foundPage)); //페이지네이션 세팅

    List<CounselBoardDTO> boardItems = foundPage.getContent().stream()
        .map(c -> c.toDto())
        .toList();

    counselBoardListDTO.setBoardItems(boardItems);
    return counselBoardListDTO;

  }

  public CounselBoardDTO readCounselBoard(Long boardId) {
    return counselBoardRepository.findById(boardId)
        .orElseThrow(() -> new CounselBoardException(CounselBoardExceptionEnum.C001))
        .toDto();

  }


  public void addCounselBoard(CounselBoardContentDTO contentDTO) {
    String content = contentDTO.getContent();
    CounselBoard board = CounselBoard.builder()
        .reply("N")
        .content(content)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
    counselBoardRepository.save(board);
  }
}
