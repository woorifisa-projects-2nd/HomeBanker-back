package fisa.dev.homebanker.domain.video.entity;

import java.time.LocalDateTime;

import fisa.dev.homebanker.domain.login.entity.User;
import fisa.dev.homebanker.domain.video.dto.CounselDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Counsel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long counselId;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customerId;

	@ManyToOne
	@JoinColumn(name = "banker_id")
	private User bankerId;

	@Column
	private String counselType;

	@Column
	private String content;

	@Column
	private LocalDateTime startedAt;

	@Column
	private LocalDateTime endedAt;

	public CounselDTO toDto(){
		return CounselDTO.builder()
			.customerId(customerId.getLoginId())
			.bankerId(bankerId.getLoginId())
			.counselType(counselType)
			.content(content)
			.startedAt(startedAt)
			.endedAt(endedAt)
			.build();
	}
}
