package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
      counselBoardRepository.save(found);

    } else {
      throw new NoSuchElementException();
    }
  }

}
