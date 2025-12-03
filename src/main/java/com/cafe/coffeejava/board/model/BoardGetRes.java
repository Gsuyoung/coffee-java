package com.cafe.coffeejava.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardGetRes {
    @Schema(title = "공지사항 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long boardId;
    @JsonIgnore
    private Long userId;
    @Schema(title = "닉네임", example = "관리자")
    private String nickname;
    @Schema(title = "공지사항 제목", example = "공지사항1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String boardTitle;
    @Schema(title = "작성일", example = "YYYY-MM-DD")
    private String createdAt;
}