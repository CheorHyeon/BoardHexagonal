package com.example.hexagonal.board.domain;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
	List<Board> findAll();
	Optional<Board> findById(Long id);
	Board save(Board board);
	void deleteById(Long id);
}