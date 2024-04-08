package fisa.dev.homebanker.global.config;

import fisa.dev.homebanker.domain.login.jwt.JwtFilter;
import fisa.dev.homebanker.domain.login.jwt.JwtUtil;
import fisa.dev.homebanker.domain.login.jwt.LoginFilter;
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

@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // CORS 설정
//  CorsConfigurationSource corsConfigurationSource() {
//    return request -> {
//      CorsConfiguration config = new CorsConfiguration();
//      config.setAllowedHeaders(Collections.singletonList("*"));
//      config.setAllowedMethods(Collections.singletonList("*"));
//      config.setAllowedOriginPatterns(
//          Collections.singletonList("http://localhost:3000")); // 허용할 origin
//      config.setAllowCredentials(true);
//      return config;
//    };
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(auth -> auth.disable())
        .formLogin(auth -> auth.disable())
        .httpBasic(auth -> auth.disable())

        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/h2-console/**").permitAll()
//            .requestMatchers("/**").permitAll()
            .requestMatchers("/login", "/", "/register").permitAll()
            .requestMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated())

        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)

        .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
            UsernamePasswordAuthenticationFilter.class)

        .headers((headerConfig) ->
            headerConfig.frameOptions(frameOptionsConfig ->
                frameOptionsConfig.disable()
            )
        );
    return http.build();

  }
}