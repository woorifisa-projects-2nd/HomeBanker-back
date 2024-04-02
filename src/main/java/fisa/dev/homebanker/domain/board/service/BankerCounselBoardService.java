package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.exception.CounselBoardException;
import fisa.dev.homebanker.domain.board.exception.CounselBoardExceptionEnum;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankerCounselBoardService {

  private final CounselBoardRepository counselBoardRepository;

  public void deleteBoard(Long boardId) {
    counselBoardRepository.deleteById(boardId);
  }

  public void updateReply(Long boardId) {
    Optional<CounselBoard> optionalCounselBoard = counselBoardRepository.findById(boardId);
    if (optionalCounselBoard.isPresent()) {
      CounselBoard found = optionalCounselBoard.get();
      found.setReply("Y");
      found.setUpdatedAt(LocalDateTime.now());

    } else {
      throw new CounselBoardException(CounselBoardExceptionEnum.C001);
    }
  }

}
