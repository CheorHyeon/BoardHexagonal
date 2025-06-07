package com.example.hexagonal.board.adapters.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.hexagonal.board.adapters.out.persistence.entity.BoardEntity;
import com.example.hexagonal.board.domain.Board;
import com.example.hexagonal.board.domain.BoardRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryAdapter implements BoardRepository {

	private final SpringDataBoardRepository jpa;

	@Override
	public List<Board> findAll() {
		return jpa.findAll().stream()
			.map(BoardEntity::toDomain)
			.toList();
	}

	@Override
	public Optional<Board> findById(Long id) {
		return jpa.findById(id)
			.map(BoardEntity::toDomain);
	}

	@Override
	public Board save(Board b) {
		BoardEntity entity = BoardEntity.fromDomain(b);
		BoardEntity saved = jpa.save(entity);
		return saved.toDomain();
	}

	@Override
	public void deleteById(Long id) {
		jpa.deleteById(id);
	}
}
