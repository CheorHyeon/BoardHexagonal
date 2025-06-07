package com.example.hexagonal.board.domain;

public class Board {
	private Long id;
	private String title;
	private String content;
	private String author;

	public Board() {
	}

	public Board(Long id, String title, String content, String author) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public static Board of(Long id, String title, String content, String author) {
		return new Board(id, title, content, author);
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
