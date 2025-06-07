package com.example.board.dto;

import com.example.board.entity.Board;

import lombok.Builder;

@Builder
public record BoardDetail(
	Long id,
	String title,
	String content,
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
