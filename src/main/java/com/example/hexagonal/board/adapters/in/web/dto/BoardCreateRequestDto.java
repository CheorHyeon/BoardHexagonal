package com.example.hexagonal.board.adapters.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoardCreateRequestDto(
	@Schema(description = "작성할 게시글 제목", example = "제목입니다.")
	String title,
	@Schema(description = "작성할 게시글 내용", example = "내용입니다.")
	String content,
	@Schema(description = "작성자 이름", example = "홍길동")
	String author
) {
}
