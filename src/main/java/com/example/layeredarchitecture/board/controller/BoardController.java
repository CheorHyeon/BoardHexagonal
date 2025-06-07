package com.example.layeredarchitecture.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.layeredarchitecture.board.dto.AllBoardResponse;
import com.example.layeredarchitecture.board.dto.BoardCreateRequestDto;
import com.example.layeredarchitecture.board.dto.BoardDetail;
import com.example.layeredarchitecture.board.dto.UpdateBoardRequestDto;
import com.example.layeredarchitecture.board.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "레이어드 아키텍처 게시판 컨트롤러")
public class BoardController {

	private final BoardService boardService;

	@GetMapping
	@Operation(summary = "게시글 전체 조회", description = "게시글 전체 목록 조회")
	public AllBoardResponse getBoards() {
		return boardService.getAllBoards();
	}

	@GetMapping("/{id}")
	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회")
	public ResponseEntity<BoardDetail> getBoardById(
		@PathVariable @Schema(description = "조회할 게시글 id", example = "1") Long id) {
		return ResponseEntity.ok(boardService.getBoardById(id));
	}

	@PostMapping
	@Operation(summary = "게시글 작성 API", description = "게시글을 작성합니다.")
	public ResponseEntity<BoardDetail> createBoard(@RequestBody BoardCreateRequestDto newBoard) {
		return ResponseEntity.ok(boardService.createBoard(newBoard));
	}

	@PatchMapping("/{id}")
	@Operation(summary = "게시글 수정", description = "id에 해당하는 게시글을 수정합니다.")
	public ResponseEntity<BoardDetail> updateBoard(
		@PathVariable @Schema(description = "수정할 게시글 id", example = "1") Long id,
		@RequestBody UpdateBoardRequestDto updateData) {
		return ResponseEntity.ok(boardService.updateBoard(id, updateData));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "게시글 삭제", description = "id에 해당하는 게시글을 삭제합니다.")
	public ResponseEntity<Void> deleteBoard(@PathVariable @Schema(description = "삭제할 게시글 id", example = "1") Long id) {
		boardService.deleteBoard(id);
		return ResponseEntity.ok().build();
	}
}
