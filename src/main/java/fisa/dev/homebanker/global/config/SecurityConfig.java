package fisa.dev.homebanker.global.config;

import fisa.dev.homebanker.domain.login.controller.JwtTokenFilter;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenFilter jwtTokenFilter;

  // CORS 설정
  CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedHeaders(Collections.singletonList("*"));
      config.setAllowedMethods(Collections.singletonList("*"));
      config.setAllowedOriginPatterns(
          Collections.singletonList("http://localhost:3000")); // 허용할 origin
      config.setAllowCredentials(true);
      return config;
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // JWT는 상태를 유지하지 않으므로, 세션을 사용하지 않고 CSRF 보호가 필요없음
        .httpBasic(httpBasic -> httpBasic.disable())
        .csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> formLogin.disable())
        .sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 필터 적용
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

        // 권한 설정
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/**").permitAll()

//                .requestMatchers("/api/admin/login").authenticated()
//                .requestMatchers("/api/admin/**").hasAuthority(UserRole.ADMIN.name())

                .anyRequest().authenticated())

        .headers((headerConfig) ->
            headerConfig.frameOptions(frameOptionsConfig ->
                frameOptionsConfig.disable()
            )
        );
    return http.build();

  }
}