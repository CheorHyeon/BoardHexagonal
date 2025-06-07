package com.example.layeredarchitecture.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.layeredarchitecture.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
