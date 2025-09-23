package com.joo.joo.domain.board.controller;

import com.joo.joo.domain.board.dto.request.CreateBoardRequest;
import com.joo.joo.domain.board.dto.response.BoardResponse;
import com.joo.joo.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Tag(name = "Board", description = "게시판 API")
public class BoardController {


    private final BoardService boardService;

    @GetMapping
    @Operation(summary = "모든 게시글 조회")
    public List<BoardResponse> getAllBoards() {
        return boardService.findAll();
    }


    @GetMapping("/{id}")
    @Operation(summary = "게시글 ID로 조회")
    public BoardResponse getBoard(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @PostMapping
    @Operation(summary = "게시글 저장")
    public BoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        return boardService.save(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}