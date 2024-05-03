package fisa.dev.homebanker.domain.video.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fisa.dev.homebanker.domain.video.dto.CounselDTO;
import fisa.dev.homebanker.domain.video.service.CounselService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/counsel")
@RequiredArgsConstructor
public class CounselController {
	private final CounselService counselService;

	@PostMapping("/register")
	public ResponseEntity<CounselDTO> registerCounsel(@RequestBody CounselDTO dto){
	    return ResponseEntity.ok(counselService.registerCounsel(dto));
	}
}
