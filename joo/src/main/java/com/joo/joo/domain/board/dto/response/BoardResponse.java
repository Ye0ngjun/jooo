package com.joo.joo.domain.board.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private Long id;
    private String title;
    private String writer;
    private String content;

}
