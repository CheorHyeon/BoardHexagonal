package com.example.layeredarchitecture.board.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AllBoardResponse(
	List<BoardDetail> boardDetailList
) {
	public static AllBoardResponse from(List<BoardDetail> boardDetailList) {
		return AllBoardResponse.builder()
			.boardDetailList(boardDetailList)
			.build();
	}
}
