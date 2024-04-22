package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.MyPageDTO;
import fisa.dev.homebanker.domain.login.dto.ProductRegisterDTO;
import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.exception.UserException;
import fisa.dev.homebanker.domain.login.exception.UserExceptionEnum;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.domain.product.entity.Sale;
import fisa.dev.homebanker.domain.product.repository.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final SaleRepository saleRepository;
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

  public MyPageDTO readMyPage() {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    User customer = userRepository.findByLoginId(loginId);
    if(!"ROLE_CUSTOMER".equals(customer.getRole())) {
      throw new UserException(UserExceptionEnum.P002);
    }
    return MyPageDTO.builder()
        .name(customer.getName())
        .joinDate(customer.getJoinDate())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .build();
  }

  public MyPageDTO updateMyPage(MyPageDTO myPageDTO) {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    User customer = userRepository.findByLoginId(loginId);
    if(!"ROLE_CUSTOMER".equals(customer.getRole())) {
      throw new UserException(UserExceptionEnum.P002);
    }
    customer.setName(myPageDTO.getName());
    customer.setPhone(myPageDTO.getPhone());
    customer.setAddress(myPageDTO.getAddress());

    userRepository.save(customer);

    return MyPageDTO.builder()
        .name(customer.getName())
        .joinDate(customer.getJoinDate())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .build();
  }

//  public ProductRegisterDTO readProductRegister(Integer id) {
//    User customer = userRepository.findById(id)
//        .orElseThrow(() -> new UserException(UserExceptionEnum.P001));
//    if(!"ROLE_CUSTOMER".equals(customer.getRole())) {
//      throw new UserException(UserExceptionEnum.P002);
//    }
//
//    // 이 때, exception 처리를 하는게 맞는지 확인하기
//    Sale sale = saleRepository.findByCustomerId(customer.getId())
//        .orElseThrow(() -> new UserException((UserExceptionEnum.P004)));
//
//  }
}