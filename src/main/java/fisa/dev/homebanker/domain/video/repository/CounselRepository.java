package fisa.dev.homebanker.domain.video.repository;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fisa.dev.homebanker.domain.video.entity.Counsel;

@Repository
public interface CounselRepository extends JpaRepository<Counsel, Long> {
}
