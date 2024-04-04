package fisa.dev.homebanker.domain.login.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// OncePerRequestFilter : 매번 들어갈 때마다 확인해주는 필터
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();

    // 아래 경로는 이 필터가 적용되지 않는다. -> 로그인을 하지 않은 상태이므로 토큰이 없기 때문
    if (path.startsWith("/api/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authorizationHeader = request.getHeader("Authorization");

    String token = null;
    String loginId = null;

    // Header에서 Bearer 부분 이하로 붙은 token을 파싱한다.
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
    }

    loginId = jwtTokenUtil.extractUserName(token);
    if (loginId == null) {
      return;
    }

    UserDetails userDetails = userDetailService.loadUserByUsername(loginId); // 괄호 안의 값은 PK

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
          = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      usernamePasswordAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    filterChain.doFilter(request, response);
  }
}