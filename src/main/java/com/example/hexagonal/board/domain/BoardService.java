package com.example.hexagonal.board.domain;

import java.util.List;

public interface BoardService {
	List<Board> getAllBoards();
	Board getBoard(Long id);
	Board createBoard(Board board);
	Board updateBoard(Long id, Board board);
	void deleteBoard(Long id);
}
