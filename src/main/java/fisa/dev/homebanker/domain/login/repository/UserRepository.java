package fisa.dev.homebanker.domain.login.repository;

import fisa.dev.homebanker.domain.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByLoginId(String loginId);
}