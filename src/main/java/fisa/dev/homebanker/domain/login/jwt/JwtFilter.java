package fisa.dev.homebanker.domain.login.jwt;

import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.Customer;
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

  // request에서 뽑아내서 jwt 검증 진행
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    //request에서 Authorization 헤더 찾기
    String authorization = request.getHeader("Authorization");

    //Authorization 헤더 검증
    if (authorization == null || !authorization.startsWith("Bearer ")) {

      System.out.println("token null");
      filterChain.doFilter(request, response);

      //현재 filterchain에서의 처리가 종료되면 종료
      return;
    }

    String token = authorization.split(" ")[1];

    //토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {

      System.out.println("token expired");
      filterChain.doFilter(request, response);

      return;
    }

    // 토큰에서 loginId와 role 가져오기
    String loginId = jwtUtil.getLoginId(token);
    String role = jwtUtil.getRole(token);
//    System.out.println("role = " + role);

    Customer customer = new Customer();
    customer.setCustomerLoginId(loginId);
    //JWT가 유효하다면, 사용자가 누구인지 이미 알고 있는 상태이며, 추가적인 비밀번호 검증은 필요하지 않음
    //비밀번호 검증을 건너뛰고 사용자의 식별 정보만을 사용하기 위함
    customer.setCustomerLoginPw("temp");
    customer.setCustomerRole(role);

    //CustomUserDetails에 customer 객체 담기
    CustomUserDetails userDetails = new CustomUserDetails(customer);

    //스프링 시큐리티 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());

    System.out.println(authToken);

    //지금 요청에 대해 user 세션을 생성
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
