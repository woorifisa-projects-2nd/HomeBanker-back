package fisa.dev.homebanker.domain.video.service;

import org.springframework.stereotype.Service;

import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.domain.video.dto.CounselDTO;
import fisa.dev.homebanker.domain.video.entity.Counsel;
import fisa.dev.homebanker.domain.video.repository.CounselRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CounselService {
	private final UserRepository userRepository;
	private final CounselRepository counselRepository;

	public CounselDTO registerCounsel(CounselDTO counselDTO){
		User customer = userRepository.findByLoginId(counselDTO.getCustomerId());
		User banker = userRepository.findByLoginId(counselDTO.getBankerId());
		Counsel counsel = counselRepository.save(Counsel.builder()
			.customerId(customer)
			.bankerId(banker)
			.counselType(counselDTO.getCounselType())
			.content(counselDTO.getContent())
			.startedAt(counselDTO.getStartedAt())
			.endedAt(counselDTO.getEndedAt())
			.build());
		return counsel.toDto();
	}
}
