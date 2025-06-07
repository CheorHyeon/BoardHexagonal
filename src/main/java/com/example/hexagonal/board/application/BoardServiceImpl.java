package com.example.hexagonal.board.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal.board.domain.Board;
import com.example.hexagonal.board.domain.BoardRepository;
import com.example.hexagonal.board.domain.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository repo;

	@Override
	public List<Board> getAllBoards() {
		return repo.findAll();
	}

	@Override
	public Board getBoard(Long id) {
		return repo.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Not found"));
	}

	@Override
	@Transactional
	public Board createBoard(Board board) {
		return repo.save(board);
	}

	@Override
	@Transactional
	public Board updateBoard(Long id, Board board) {
		Board exist = getBoard(id);
		exist.updateContent(board.getContent());
		exist.updateTitle(board.getTitle());
		return repo.save(exist);
	}

	@Override
	@Transactional
	public void deleteBoard(Long id) {
		repo.deleteById(id);
	}
}
