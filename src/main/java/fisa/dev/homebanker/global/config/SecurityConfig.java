package fisa.dev.homebanker.global.config;

//import fisa.dev.homebanker.domain.login.controller.JwtTokenFilter;

import fisa.dev.homebanker.domain.login.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserService userService;

  // 회원의 패스워드 암호화
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // ⭐️ CORS 설정
  CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedHeaders(Collections.singletonList("*"));
      config.setAllowedMethods(Collections.singletonList("*"));
      config.setAllowedOriginPatterns(
          Collections.singletonList("http://localhost:3000")); // ⭐️ 허용할 origin
      config.setAllowCredentials(true);
      return config;
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic(httpBasic -> httpBasic.disable())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .addFilterBefore(new JwtTokenFilter(userService, secretKey),
//            UsernamePasswordAuthenticationFilter.class)

        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                .requestMatchers("/api/info").authenticated()
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