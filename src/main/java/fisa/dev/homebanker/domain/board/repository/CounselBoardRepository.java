package fisa.dev.homebanker.domain.board.repository;

import fisa.dev.homebanker.domain.board.entity.CounselBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounselBoardRepository extends JpaRepository<CounselBoard, Long> {

  Page<CounselBoard> findByCustomerId(Integer id, Pageable pageable);
}
