package fisa.dev.homebanker.domain.login.service;

import fisa.dev.homebanker.domain.login.dto.MyPageDTO;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import fisa.dev.homebanker.domain.login.dto.UserRegisterDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.exception.UserException;
import fisa.dev.homebanker.domain.login.exception.UserExceptionEnum;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.domain.product.entity.Sale;
import fisa.dev.homebanker.domain.product.exception.ProductException;
import fisa.dev.homebanker.domain.product.exception.ProductionExceptionEnum;
import fisa.dev.homebanker.domain.product.repository.SaleRepository;
import fisa.dev.homebanker.global.util.pagination.PaginationResMaker;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
  private final PaginationResMaker paginationResMaker;

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

    return MyPageDTO.builder()
        .name(customer.getName())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .build();
  }

  public SaleListDTO findAllSales(Integer size, Integer page) {
    if(page < 0) {
      throw new ProductException(ProductionExceptionEnum.POO2);
    }
    if (size <= 0) {
      throw new ProductException(ProductionExceptionEnum.P004);
    }
    SaleListDTO saleListDTO = new SaleListDTO();
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "createdAt");
    Page<Sale> foundPage = saleRepository.findAll(pageable);
    saleListDTO.setPagination(paginationResMaker.makePaginationDto(foundPage));
    List<SaleDTO> saleItems = foundPage.get()
        .map(sale -> sale.toDto())
        .toList();
    saleListDTO.setSaleItems(saleItems);
    return saleListDTO;
  }
}