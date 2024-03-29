package fisa.dev.homebanker.domain.board.entity;

import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselBoard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;

  //FK 설정 추가해야함.

  @Column(length = 500, nullable = false)
  private String content;

  @Column(name = "reply_YN", nullable = false)
  private String reply;
  //default value를 N으로 설정해야함

  @Column(nullable = false)

  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public CounselBoardDTO toDto() {
    return CounselBoardDTO.builder()
        //.customerId()
        //.customerName()
        .boardId(boardId)
        .content(content)
        .replyYN(reply)
        //.telephone()
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }

  public void setReply(String reply) {
    this.reply = reply;
  }
}
