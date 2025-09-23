package com.joo.joo.domain.board.service;

import com.joo.joo.domain.board.dto.request.CreateBoardRequest;
import com.joo.joo.domain.board.dto.response.BoardResponse;
import com.joo.joo.domain.board.entity.Board;
import com.joo.joo.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse> findAll() {
        return boardRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public BoardResponse findById(Long id) {
        return boardRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }



    public BoardResponse save(CreateBoardRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .writer(request.getWriter())
                .content(request.getContent())
                .build();

        Board saved = boardRepository.save(board);


        return BoardResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .writer(saved.getWriter())
                .content(saved.getContent())
                .build();
    }
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }



    private BoardResponse convertToDTO(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .build();
    }
}