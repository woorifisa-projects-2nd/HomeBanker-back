package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.dto.PaginationDTO;
import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

  public CounselBoardListDTO readAllCounselBoards(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(size, page);
    Page<CounselBoard> foundPage = counselBoardRepository.findAll(pageable);

    CounselBoardListDTO counselBoardListDTO = new CounselBoardListDTO();
    counselBoardListDTO.setPagination(makePaginationDto(foundPage)); //페이지네이션 세팅

    List<CounselBoardDTO> boardItems = new ArrayList<>();
    for (CounselBoard c : foundPage.getContent()) {

      boardItems.add(c.toDto());
    }

    counselBoardListDTO.setBoardItems(boardItems);
    return counselBoardListDTO;

  }

  public CounselBoardDTO readCounselBoard(Long boardId) {
    CounselBoardDTO counselBoardDTO;
    return counselBoardRepository.findById(boardId).orElseThrow().toDto();

  }


  public PaginationDTO makePaginationDto(Page<CounselBoard> pages) {

    Long totalElements = pages.getTotalElements(); //전체 아이템 수
    Integer pageNumber = pages.getPageable().getPageNumber(); //현재 페이지 번호
    Integer totalPages = pages.getTotalPages(); //전체 페이지 번호

    return PaginationDTO.builder()
        .totalElements(totalElements)
        .totalPages(totalPages)
        .pageNumber(pageNumber)
        .build();
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
