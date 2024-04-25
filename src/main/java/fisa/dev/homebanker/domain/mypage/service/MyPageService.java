package fisa.dev.homebanker.domain.mypage.service;

import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.login.exception.UserException;
import fisa.dev.homebanker.domain.login.exception.UserExceptionEnum;
import fisa.dev.homebanker.domain.login.repository.UserRepository;
import fisa.dev.homebanker.domain.mypage.dto.MyPageProfileDTO;
import fisa.dev.homebanker.domain.product.dto.SaleDTO;
import fisa.dev.homebanker.domain.product.dto.SaleListDTO;
import fisa.dev.homebanker.domain.product.entity.Sale;
import fisa.dev.homebanker.domain.product.exception.ProductException;
import fisa.dev.homebanker.domain.product.exception.ProductionExceptionEnum;
import fisa.dev.homebanker.domain.product.repository.SaleRepository;
import fisa.dev.homebanker.global.util.pagination.PaginationResMaker;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MyPageService {

  private final SaleRepository saleRepository;
  private final PaginationResMaker paginationResMaker;
  private final UserRepository userRepository;

  public MyPageProfileDTO readMyPage() {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    User customer = userRepository.findByLoginId(loginId);
    if (!"ROLE_CUSTOMER" .equals(customer.getRole())) {
      throw new UserException(UserExceptionEnum.P002);
    }
    return MyPageProfileDTO.builder()
        .name(customer.getName())
        .joinDate(customer.getJoinDate())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .build();
  }

  public MyPageProfileDTO updateMyPage(MyPageProfileDTO myPageProfileDTO) {
    String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    User customer = userRepository.findByLoginId(loginId);
    if (!"ROLE_CUSTOMER" .equals(customer.getRole())) {
      throw new UserException(UserExceptionEnum.P002);
    }
    customer.setName(myPageProfileDTO.getName());
    customer.setPhone(myPageProfileDTO.getPhone());
    customer.setAddress(myPageProfileDTO.getAddress());

    return MyPageProfileDTO.builder()
        .name(customer.getName())
        .phone(customer.getPhone())
        .address(customer.getAddress())
        .build();
  }

  public SaleListDTO findAllSales(Integer size, Integer page, String loginId) {
    if (page < 0) {
      throw new ProductException(ProductionExceptionEnum.POO2);
    }
    if (size <= 0) {
      throw new ProductException(ProductionExceptionEnum.P004);
    }
//    User user = saleRepository.findByCustomer(loginId);
//    if(user == null) {
//      throw new UserException(UserExceptionEnum.P004);
//    }
    Pageable pageable = PageRequest.of(page, size, Direction.DESC, "createdAt");
    Page<Sale> foundPage = saleRepository.findAllByCustomer_LoginId(loginId, pageable);
    if (foundPage.isEmpty()) {
      throw new UserException(UserExceptionEnum.P004);
    }
    SaleListDTO saleListDTO = new SaleListDTO();
    saleListDTO.setPagination(paginationResMaker.makePaginationDto(foundPage));
    List<SaleDTO> saleItems = foundPage.get()
        .map(sale -> sale.toDto())
        .toList();
    saleListDTO.setSaleItems(saleItems);
    return saleListDTO;
  }

}
