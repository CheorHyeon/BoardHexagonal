package com.example.hexagonal.board.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hexagonal.board.adapters.out.persistence.entity.BoardEntity;

public interface SpringDataBoardRepository extends JpaRepository<BoardEntity, Long> {
}