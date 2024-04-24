package fisa.dev.homebanker.domain.video.controller;

import fisa.dev.homebanker.domain.board.dto.IdCardContentDTO;
import fisa.dev.homebanker.domain.video.service.IdentifyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  public boolean identifyUser(@RequestBody IdCardContentDTO idCardContentDTO) {
    System.out.println(idCardContentDTO.getUserName());
    return identifyUserService.identifyUser(idCardContentDTO);
  }
}
