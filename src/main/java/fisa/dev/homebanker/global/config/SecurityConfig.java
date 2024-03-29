//package fisa.dev.homebanker.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//  @Bean
//  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(Customizer.withDefaults())
//        .sessionManagement((sessionManagement) ->
//            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        )
//        .authorizeHttpRequests((authorizeRequests) ->
//            authorizeRequests.anyRequest().permitAll()
//        );
//
//    return http.build();
//  }
//}
