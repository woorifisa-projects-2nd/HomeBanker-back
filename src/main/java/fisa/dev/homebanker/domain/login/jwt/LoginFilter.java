package fisa.dev.homebanker.domain.login.jwt;

import fisa.dev.homebanker.domain.log.service.LogService;
import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final LogService logService;
  private final UserRepository userRepository;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {

    //클라이언트 요청에서 username, password 추출
    String loginId = obtainLoginId(request);
    String loginPw = obtainLoginPw(request);

    //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId,
        loginPw, null);

    //token에 담은 검증을 위한 AuthenticationManager로 전달
    return authenticationManager.authenticate(authToken);
  }

  public String obtainLoginId(HttpServletRequest request) {
    return request.getParameter("loginId");
  }

  public String obtainLoginPw(HttpServletRequest request) {
    return request.getParameter("loginPw");
  }

  //로그인 성공시 자동으로 실행하는 메소드
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) {
    // 인증된 사용자 정보 가져오기
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    // logindId 뽑아내기
    String loginId = customUserDetails.getUsername();

    // role 뽑아내기
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    // JwtUtil에 token을 만들어달라고 전달
    String token = jwtUtil.createJwt(loginId, role, 60 * 60 * 24 * 1000L); // 1일
    response.addHeader("Authorization", "Bearer " + token);

    log.info("로그인 = {} / {}", role, loginId);

    if (role.equals("ROLE_CUSTOMER")) {
      logService.customerLoginService(loginId);
    } else if (role.equals("ROLE_ADMIN")) {
      logService.bankerLoginService(loginId);
    }

    User user = userRepository.findByLoginId(loginId);
    user.setRecentLogin(LocalDateTime.now());
    userRepository.save(user);

  }

  //로그인 실패시 실행하는 메소드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) {
    response.setStatus(401);
  }
}