package com.example.hexagonal.board.adapters.out.persistence.entity;

import com.example.hexagonal.board.domain.Board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hexxagonal_board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;
	private String author;


	// --- 도메인 객체로 변환 ---
	public Board toDomain() {
		return Board.of(id, title, content, author);
	}

	// --- 도메인 객체를 엔티티로 변환 ---
	public static BoardEntity fromDomain(Board board) {
		return new BoardEntity(
			board.getId(),
			board.getTitle(),
			board.getContent(),
			board.getAuthor()
		);
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContent(String content) {
		this.content = content;
	}

}