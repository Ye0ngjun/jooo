package com.joo.joo.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "게시글 DTO")
public class CreateBoardRequest {
    @Schema(description = "제목", example = "핑거 프로젝트")
    private String title;

    @Schema(description = "작성자", example = "홍길동")
    private String writer;

    @Schema(description = "내용", example = "3개월 프로젝트를 진행 중입니다.")
    private String content;
}