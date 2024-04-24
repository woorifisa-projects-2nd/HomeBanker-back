package fisa.dev.homebanker.domain.video.service;

import fisa.dev.homebanker.domain.board.dto.IdCardContentDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class IdentifyUserService {

  public boolean identifyUser(IdCardContentDTO idCardContentDTO) {
    return true;
  }

}

