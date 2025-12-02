package com.cafe.coffeejava.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardPostReq {
    @JsonIgnore
    private long boardId;
    @Schema(title = "유저 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(title = "공지사항 제목", example = "공지사항1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String boardTitle;
    @Schema(title = "공지사항 내용", example = "공지사항입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String boardContent;
}
