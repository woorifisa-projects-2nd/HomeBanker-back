package fisa.dev.homebanker.domain.login.jwt;

import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    System.out.println("request.getRequestURI() = " + request.getRequestURI());

    String authorization = request.getHeader("Authorization");

    // Authorization 헤더 검증
    if (authorization == null || !authorization.startsWith("Bearer ")) {

      filterChain.doFilter(request, response);

      return;
    }

    String token = authorization.split(" ")[1];

    // 토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {

      System.out.println("Token Expired");
      filterChain.doFilter(request, response);

      return;
    }

    // 토큰에서 loginId와 role 가져오기
    String loginId = jwtUtil.getLoginId(token);
    String role = jwtUtil.getRole(token);

    User user = new User();
    user.setLoginId(loginId);

    // JWT가 유효하다면 사용자가 누구인지 이미 알고 있는 상태이며, 추가적인 비밀번호 검증은 필요하지 않음
    // 비밀번호 검증을 건너뛰고 사용자의 식별 정보만을 사용하기 위함
    user.setLoginPw("temp");
    user.setRole(role);

    //CustomUserDetails에 user 객체 담기
    CustomUserDetails userDetails = new CustomUserDetails(user);

    // Spring Security 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());

    // 현재 Security Context에 토큰 인증한 유저 정보 저장
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}