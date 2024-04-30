package fisa.dev.homebanker.domain.video.service;

import fisa.dev.homebanker.domain.board.dto.IdCardContentDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class IdentifyUserService {

  private WebClient webClient;

  @PostConstruct // 의존성 주입이 이루어진 후 초기화를 수행
  public void initWebClient() {
    webClient = WebClient.create("http://localhost:8081/verification");
  }

  public boolean identifyUser(IdCardContentDTO idCardContentDTO) {
    try {
      // WebClient를 사용하여 POST 요청 보내기
      String response = webClient.post()
          .body(BodyInserters.fromValue(idCardContentDTO))
          .retrieve()
          .bodyToMono(String.class)
          .block(); // 동기적으로 응답을 기다리고 결과를 반환

      // 응답 확인 후 처리
      return "true".equals(response);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}