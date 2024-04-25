package fisa.dev.homebanker.domain.product.repository;

import fisa.dev.homebanker.domain.product.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
  Page<Sale> findAllByCustomerLoginId_LoginId(String loginId, Pageable pageable);
}
