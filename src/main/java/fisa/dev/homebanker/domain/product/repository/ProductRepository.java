package fisa.dev.homebanker.domain.product.repository;

import fisa.dev.homebanker.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAll(Pageable pageable);

  Page<Product> findByProductCodeTypeName(String typeName, Pageable pageable);
}
