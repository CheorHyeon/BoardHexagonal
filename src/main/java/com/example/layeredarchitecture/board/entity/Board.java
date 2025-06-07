package com.example.layeredarchitecture.board.entity;

import com.example.layeredarchitecture.board.dto.BoardCreateRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	private String author;

	public static Board from(BoardCreateRequestDto dto) {
		return Board.builder()
			.author(dto.author())
			.title(dto.title())
			.content(dto.content())
			.build();
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}