package fisa.dev.homebanker.domain.video.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CounselDTO {

	@NotBlank(message = "필수 항목입니다.")
	private String bankerId;

	@NotBlank(message = "필수 항목입니다.")
	private String customerId;

	@NotBlank(message = "필수 항목입니다.")
	private String counselType;

	@NotBlank(message = "필수 항목입니다.")
	private LocalDateTime startedAt;

	@NotBlank(message = "필수 항목입니다.")
	private LocalDateTime endedAt;

	@NotBlank(message = "필수 항목입니다.")
	private String content;





}
