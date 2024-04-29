package fisa.dev.homebanker.global.config;

import fisa.dev.homebanker.domain.log.service.LogService;
import fisa.dev.homebanker.domain.login.jwt.JwtFilter;
import fisa.dev.homebanker.domain.login.jwt.JwtUtil;
import fisa.dev.homebanker.domain.login.jwt.LoginFilter;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;
  private final LogService logService;
  private final UserRepository userRepository;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(auth -> auth.disable())
        .formLogin(auth -> auth.disable())
        .httpBasic(auth -> auth.disable())

        .cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
            //configuration.addAllowedOrigin("https://homebanker-f.shop");//
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            configuration.setExposedHeaders(Collections.singletonList("Authorization"));

            return configuration;
          }
        }))

        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/", "/register", "/login", "/exit", "/api/board/**", "/error",
                "/api/product/**").permitAll()
            .requestMatchers("/api/banker/**").hasRole("ADMIN")
            .requestMatchers("/api/mypage/**").hasRole("CUSTOMER")
            .anyRequest().authenticated())

        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)

        .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,
                logService, userRepository),
            UsernamePasswordAuthenticationFilter.class)

        .headers((headerConfig) ->
            headerConfig.frameOptions(frameOptionsConfig ->
                frameOptionsConfig.disable()
            )
        );
    return http.build();

  }
}