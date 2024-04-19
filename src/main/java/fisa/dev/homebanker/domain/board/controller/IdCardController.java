package fisa.dev.homebanker.domain.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fisa.dev.homebanker.domain.board.dto.IdCardContentDTO;
import fisa.dev.homebanker.domain.board.service.IdentifyUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IdCardController {

	private final IdentifyUserService identifyUserService;
	/**
	 * 신분증 확인
	 *
	 * @param idCardContentDTO 신분증 내용
	 */
	@PostMapping("/api/banker/video/identify")
	public boolean identifyUser(@RequestBody IdCardContentDTO idCardContentDTO){
		System.out.println(idCardContentDTO.getUserName());
		return identifyUserService.identifyUser(idCardContentDTO);
	}
}
