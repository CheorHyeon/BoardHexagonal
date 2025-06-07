package com.example.hexagonal.board.adapters.in.web.mapper;

import org.springframework.stereotype.Component;

import com.example.hexagonal.board.adapters.in.web.dto.BoardCreateRequestDto;
import com.example.hexagonal.board.adapters.in.web.dto.UpdateBoardRequestDto;
import com.example.hexagonal.board.domain.Board;

@Component
public class BoardRequestMapper {

	// domain entity가 dto 존재 모르게 값 꺼내서 할당해야 함
	public Board toDomainForCreate(BoardCreateRequestDto dto) {
		// id는 아직 모르니 null
		return Board.of(null, dto.title(), dto.content(), dto.author());
	}

	// domain entity가 dto 존재 모르게 값 꺼내서 할당해야 함
	public Board toDomainForUpdate(UpdateBoardRequestDto dto) {
		// id는 아직 모르니 null
		return Board.of(null, dto.title(), dto.content(), null);
	}
}
