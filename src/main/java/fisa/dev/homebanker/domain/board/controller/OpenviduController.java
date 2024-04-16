package fisa.dev.homebanker.domain.board.controller;

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

  private static Map<String, Integer> sessionMap = new ConcurrentHashMap<String, Integer>();

  @PostConstruct
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
  }

  @PostMapping("/api/sessions")
  public ResponseEntity<String> initializeSession(
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    String sessionId = String.valueOf(params.get("customSessionId"));
    Integer cnt = sessionMap.get(sessionId);

    if (cnt != null && cnt >= 2) {
      return new ResponseEntity<>("full", HttpStatus.OK);
    } else {
      sessionMap.put(sessionId, sessionMap.getOrDefault(sessionId, 0) + 1);
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
