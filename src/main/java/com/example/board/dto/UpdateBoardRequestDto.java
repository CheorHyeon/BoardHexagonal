package com.example.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateBoardRequestDto(
	@Schema(description = "업데이트 할 게시글 제목", example = "변경된 제목")
	String title,
	@Schema(description = "업데이트 할 게시글 내용", example = "변경된 내용")
	String content
) {
}
