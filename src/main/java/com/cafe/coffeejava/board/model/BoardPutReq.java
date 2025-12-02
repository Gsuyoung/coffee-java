package com.cafe.coffeejava.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardPutReq {
    @Schema(title = "공지사항 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int boardId;
    @Schema(title = "공지사항 제목")
    private String boardTitle;
    @Schema(title = "공지사항 내용")
    private String boardContent;
}