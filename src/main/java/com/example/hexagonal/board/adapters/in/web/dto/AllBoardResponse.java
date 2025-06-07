package com.example.hexagonal.board.adapters.in.web.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AllBoardResponse(
	@Schema(description = "게시글 정보 목록")
	List<BoardDetail> boardDetailList
) {
	public static AllBoardResponse from(List<BoardDetail> boardDetailList) {
		return AllBoardResponse.builder()
			.boardDetailList(boardDetailList)
			.build();
	}
}
