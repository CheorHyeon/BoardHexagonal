package com.example.hexagonal.board.adapters.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.hexagonal.board.adapters.in.web.dto.AllBoardResponse;
import com.example.hexagonal.board.adapters.in.web.dto.BoardDetail;
import com.example.hexagonal.board.domain.Board;

@Component
public class BoardResponseMapper {

	/**
	 * 단건 Dto 목록을 Dto로 감쌈 - 의존성 도메인쪽으로 의존이라 가능
	 */
	public AllBoardResponse toAllDto(List<Board> boards) {
		List<BoardDetail> list = boards.stream()
			.map(BoardDetail::from)
			.toList();

		return AllBoardResponse.from(list);
	}

	/**
	 * 단건 Dto 변환 - 의존성 도메인쪽으로 의존이라 가능
	 */
	public BoardDetail toResponseDto(Board board) {
		return BoardDetail.from(board);
	}
}
