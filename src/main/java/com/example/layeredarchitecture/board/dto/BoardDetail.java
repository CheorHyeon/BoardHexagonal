package com.example.layeredarchitecture.board.dto;

import com.example.layeredarchitecture.board.entity.Board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoardDetail(
	@Schema(description = "게시글 Id", example = "1")
	Long id,
	@Schema(description = "게시글 제목", example = "제목입니다.")
	String title,
	@Schema(description = "게시글 내용", example = "내용입니다.")
	String content,
	@Schema(description = "게시글 작성자", example = "홍길동")
	String author
) {
	public static BoardDetail from(Board board) {
		return BoardDetail.builder()
			.id(board.getId())
			.title(board.getTitle())
			.content(board.getContent())
			.author(board.getAuthor())
			.build();
	}
}
