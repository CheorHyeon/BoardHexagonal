package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.board.dto.AllBoardResponse;
import com.example.board.dto.BoardCreateRequestDto;
import com.example.board.dto.BoardDetail;
import com.example.board.dto.UpdateBoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public AllBoardResponse getAllBoards() {
		List<Board> allBoard = boardRepository.findAll();
		return AllBoardResponse.from(allBoard.stream()
			.map(BoardDetail::from)
			.toList());
	}

	public BoardDetail getBoardById(Long id) {
		return BoardDetail.from(findBoardById(id));
	}

	@Transactional
	public BoardDetail createBoard(BoardCreateRequestDto dto) {
		return BoardDetail.from(boardRepository.save(Board.from(dto)));
	}

	@Transactional
	public BoardDetail updateBoard(Long boardId, UpdateBoardRequestDto boardData) {
		Board board = findBoardById(boardId);

		if (StringUtils.hasText(boardData.content())) {
			board.updateContent(boardData.content());
		}

		if(StringUtils.hasText(boardData.title())) {
			board.updateTitle(boardData.title());
		}

		return BoardDetail.from(boardRepository.save(board));
	}

	@Transactional
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}


	private Board findBoardById(Long id) {
		return boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Board not found"));
	}
}
