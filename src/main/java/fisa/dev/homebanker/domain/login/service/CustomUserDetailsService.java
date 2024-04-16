package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.CustomUserDetails;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    User user = userRepository.findByLoginId(loginId);

    if (user != null) {
      System.out.println("로그인 정보와 일치하는 계정이 존재합니다.");
      return new CustomUserDetails(user);
    }
    System.out.println("계정이 존재하지 않습니다.");
    return null;
  }
}
