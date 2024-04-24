package fisa.dev.homebanker.domain.video.controller;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
public class OpenviduController {

  @Value("${openvidu.url}")
  private String OPENVIDU_URL;

  @Value("${openvidu.secret}")
  private String OPENVIDU_SECRET;

  private OpenVidu openvidu;

  private static Map<String, boolean[]> sessionMap = new ConcurrentHashMap<>();

  @PostConstruct
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
  }


  private String checkEntrance(boolean[] entrance, int idx) {
    if (entrance[idx] == false) {
      entrance[idx] = true;
      return "success";
    } else {
      return "full";
    }
  }

  @PostMapping("/api/sessions")
  public ResponseEntity<String> initializeSession(
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    String sessionId = String.valueOf(params.get("customSessionId"));
    String role = String.valueOf(params.get("role"));
    boolean[] entrance = sessionMap.get(sessionId); // [0] : customer, [1] : banker
    String result = "success";

    if (entrance != null && entrance[0] == true && entrance[1] == true) {
      result = "full";
    } else if (entrance != null) { //한명만 입장한 세션
      if (role.equals("ROLE_CUSTOMER")) { //고객이 입장
        result = checkEntrance(entrance, 0);
      } else { //은행원이 입장
        result = checkEntrance(entrance, 1);
      }
    } else { //아무도 입장하지 않은 세션
      sessionMap.put(sessionId, new boolean[2]);
      if (role.equals("ROLE_CUSTOMER")) {
        sessionMap.get(sessionId)[0] = true;
      } else {
        sessionMap.get(sessionId)[1] = true;
      }
    }

    if (result.equals("full")) {
      return new ResponseEntity<>("full", HttpStatus.OK);
    }

    SessionProperties properties = SessionProperties.fromJson(params).build();
    Session session = openvidu.createSession(properties);

    System.out.println(sessionMap.toString());
    return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
  }

  @PostMapping("/api/sessions/{sessionId}/connections")
  public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    Session session = openvidu.getActiveSession(sessionId);
    if (session == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
    Connection connection = session.createConnection(properties);
    return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
  }

  @PostMapping("/api/sessions/{sessionId}/destroy")
  public ResponseEntity destroyConnection(@PathVariable("sessionId") String sessionId)
      throws OpenViduJavaClientException, OpenViduHttpException {
    sessionMap.remove(sessionId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
