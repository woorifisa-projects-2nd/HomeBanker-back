package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder bCryptPasswordEncoder;

  public User register(UserRegisterDTO userRegisterDTO) {

    return userRepository.save(User.builder()
        .name(userRegisterDTO.getName())
        .birth(userRegisterDTO.getBirth())
        .phone(userRegisterDTO.getPhone())
        .address(userRegisterDTO.getAddress())
        .loginId(userRegisterDTO.getLoginId())
        .loginPw(bCryptPasswordEncoder.encode(userRegisterDTO.getLoginPw()))
        .role(userRegisterDTO.getRole())
        .identificationNum(userRegisterDTO.getIdentificationNum())
        .recentLogin(null) // 최근 로그인은 초기값으로 null 지정
        .joinDate(userRegisterDTO.getJoinDate())
        .build());
  }

  public String findRoleByLoginId(String loginId) {
    return (userRepository.findByLoginId(loginId)).getRole();
  }
}