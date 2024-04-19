package fisa.dev.homebanker.domain.board.service;
import org.springframework.stereotype.Service;

import fisa.dev.homebanker.domain.board.dto.IdCardContentDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IdentifyUserService {
	public boolean identifyUser(IdCardContentDTO idCardContentDTO){
		return true;
	}

}

