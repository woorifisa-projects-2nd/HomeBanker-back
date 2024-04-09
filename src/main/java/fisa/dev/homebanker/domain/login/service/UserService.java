package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
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
    String customerName = userRegisterDTO.getName();
    Date customerBirth = userRegisterDTO.getBirth();
    String customerPhone = userRegisterDTO.getPhone();
    String customerAddress = userRegisterDTO.getAddress();
    String customerLoginId = userRegisterDTO.getLoginId();
    String customerLoginPw = userRegisterDTO.getLoginPw();
    String customerRole = userRegisterDTO.getRole();
    String customerIdentificationNum = userRegisterDTO.getIdentificationNum();
    Date joinDate = userRegisterDTO.getJoinDate();

    User user = new User();
    user.setName(customerName);
    user.setBirth(customerBirth);
    user.setPhone(customerPhone);
    user.setAddress(customerAddress);
    user.setLoginId(customerLoginId);
    user.setLoginPw(bCryptPasswordEncoder.encode(customerLoginPw));
    user.setRole(customerRole);
    user.setIdentificationNum(customerIdentificationNum);
    user.setRecentLogin(null);
    user.setJoinDate(joinDate);
    userRepository.save(user);
    return user;
  }
}