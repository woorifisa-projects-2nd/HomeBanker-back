package fisa.dev.homebanker.domain.board.service;

import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import fisa.dev.homebanker.domain.board.exception.CounselBoardException;
import fisa.dev.homebanker.domain.board.exception.CounselBoardExceptionEnum;
import fisa.dev.homebanker.domain.board.repository.CounselBoardRepository;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
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
  private final UserRepository userRepository;

  public void deleteBoard(Long boardId) {
    counselBoardRepository.deleteById(boardId);
  }

  public void updateReply(Long boardId, String loginId) {
    User banker = userRepository.findByLoginId(loginId);
    Optional<CounselBoard> optionalCounselBoard = counselBoardRepository.findById(boardId);
    if (optionalCounselBoard.isPresent()) {
      CounselBoard found = optionalCounselBoard.get();
      found.setReply("Y");
      found.setUpdatedAt(LocalDateTime.now());
      found.setBankerId(banker.getId());
      found.setBankerName(banker.getName());

    } else {
      throw new CounselBoardException(CounselBoardExceptionEnum.C001);
    }
  }

}
