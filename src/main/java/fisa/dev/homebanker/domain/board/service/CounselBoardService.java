package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.dto.CounselBoardContentDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardListDTO;
import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.exception.CounselBoardException;
import fisa.dev.homebanker.domain.board.exception.CounselBoardExceptionEnum;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.global.util.pagination.PaginationResMaker;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselBoardService {

  private final CounselBoardRepository counselBoardRepository;
  private final PaginationResMaker paginationResMaker;
  private final UserRepository userRepository;

  public CounselBoardListDTO readAllCounselBoards(Integer page, Integer size, String loginId) {
    if (page < 0) {
      throw new CounselBoardException(CounselBoardExceptionEnum.C002);
    }
    if (size <= 0) {
      throw new CounselBoardException(CounselBoardExceptionEnum.C003);
    }
    Pageable pageable = PageRequest.of(page, size, Direction.ASC, "createdAt");

    User user = userRepository.findByLoginId(loginId);

    Page<CounselBoard> foundPage;
    if (user.getRole().equals("ROLE_ADMIN")) {
      foundPage = counselBoardRepository.findAll(pageable);
    } else {
      foundPage = counselBoardRepository.findByCustomerId(user.getId(), pageable);
    }

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


  public void addCounselBoard(CounselBoardContentDTO contentDTO, String loginId) {
    User customer = userRepository.findByLoginId(loginId);
    String content = contentDTO.getContent();
    CounselBoard board = CounselBoard.builder()
        .reply("N")
        .customer(customer)
        .content(content)
        .createdAt(LocalDateTime.now())
        .build();
    counselBoardRepository.save(board);
  }
}
