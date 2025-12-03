package com.cafe.coffeejava.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardDelReq {
    @Schema(title = "공지사항 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long boardId;
    @Schema(title = "유저 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
}