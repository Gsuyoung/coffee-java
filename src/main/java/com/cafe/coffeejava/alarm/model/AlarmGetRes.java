package com.cafe.coffeejava.alarm.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlarmGetRes {
    @Schema(title = "알림 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long alarmId;
    @Schema(title = "유형", example = "Feed / Comment")
    private String type;
    @Schema(title = "유형 pk", example = "1")
    private long targetId;
    @Schema(title = "메세지", example = "게시글에 댓글이 달렸습니다.")
    private String message;
    @Schema(title = "읽음 여부", example = "0")
    private int isRead;
    @Schema(title = "작성일", example = "YYYY-MM-DD")
    private String createdAt;
}