package com.example.hexagonal.board.adapters.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagonal.board.adapters.in.web.dto.AllBoardResponse;
import com.example.hexagonal.board.adapters.in.web.dto.BoardCreateRequestDto;
import com.example.hexagonal.board.adapters.in.web.dto.BoardDetail;
import com.example.hexagonal.board.adapters.in.web.dto.UpdateBoardRequestDto;
import com.example.hexagonal.board.adapters.in.web.mapper.BoardRequestMapper;
import com.example.hexagonal.board.adapters.in.web.mapper.BoardResponseMapper;
import com.example.hexagonal.board.domain.Board;
import com.example.hexagonal.board.domain.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hexagonal/boards")
@RequiredArgsConstructor
@Tag(name = "BoardControllerHexagonal", description = "헥사고날 게시판 컨트롤러")
public class BoardControllerHexagonal {

	private final BoardService service;
	private final BoardRequestMapper requestMapper;
	private final BoardResponseMapper responseMapper;

	@GetMapping
	@Operation(summary = "게시글 전체 조회", description = "게시글 전체 목록 조회")
	public ResponseEntity<AllBoardResponse> getAll() {
		List<Board> boards = service.getAllBoards();
		return ResponseEntity.ok(responseMapper.toAllDto(boards));
	}

	@GetMapping("/{id}")
	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회")
	public ResponseEntity<BoardDetail> getOne(
		@PathVariable @Schema(description = "조회할 게시글 id", example = "1") Long id) {
		Board board = service.getBoard(id);
		return ResponseEntity.ok(responseMapper.toResponseDto(board));
	}

	@PostMapping
	@Operation(summary = "게시글 작성 API", description = "게시글을 작성합니다.")
	public ResponseEntity<BoardDetail> create(@RequestBody BoardCreateRequestDto createRequestDto) {
		Board domain = requestMapper.toDomainForCreate(createRequestDto);
		Board createdDomainBoardEntity = service.createBoard(domain);
		return ResponseEntity.ok(responseMapper.toResponseDto(createdDomainBoardEntity));
	}

	@PatchMapping("/{id}")
	@Operation(summary = "게시글 수정", description = "id에 해당하는 게시글을 수정합니다.")
	public ResponseEntity<BoardDetail> update(@PathVariable @Schema(description = "수정할 게시글 id", example = "1") Long id,
		@RequestBody UpdateBoardRequestDto updateRequestDto) {
		Board domainForUpdate = requestMapper.toDomainForUpdate(updateRequestDto);
		Board updatedDomain = service.updateBoard(id, domainForUpdate);
		return ResponseEntity.ok(responseMapper.toResponseDto(updatedDomain));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "게시글 삭제", description = "id에 해당하는 게시글을 삭제합니다.")
	public ResponseEntity<Void> delete(@PathVariable @Schema(description = "삭제할 게시글 id", example = "1") Long id) {
		service.deleteBoard(id);
		return ResponseEntity.ok().build();
	}
}
