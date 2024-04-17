package fisa.dev.homebanker.domain.board.entity;

import fisa.dev.homebanker.domain.board.dto.BankerDTO;
import fisa.dev.homebanker.domain.board.dto.CounselBoardDTO;
import fisa.dev.homebanker.domain.login.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @JoinColumn(nullable = false)
  @ManyToOne
  private User customer;

  private Integer bankerId;

  @Column(length = 10)
  private String bankerName;

  @Column(length = 500, nullable = false)
  private String content;

  @Column(name = "reply_YN", nullable = false)
  private String reply;

  @Column(nullable = false)

  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public CounselBoardDTO toDto() {
    return CounselBoardDTO.builder()
        .customerId(Long.valueOf(customer.getId()))
        .customerName(customer.getName())
        .banker(new BankerDTO(bankerId, bankerName))
        .boardId(boardId)
        .content(content)
        .replyYN(reply)
        .telephone(customer.getPhone())
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setBankerId(Integer bankerId) {
    this.bankerId = bankerId;
  }

  public void setBankerName(String bankerName) {
    this.bankerName = bankerName;
  }
}
