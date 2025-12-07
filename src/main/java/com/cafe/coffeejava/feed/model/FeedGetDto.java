package com.cafe.coffeejava.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedGetDto {
    @Schema(title = "피드 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long feedId;
    @Schema(title = "제목", example = "공부하기 좋은 카페입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(title = "유저 닉네임", example = "나는홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
    @Schema(title = "작성일", example = "2025-12-07")
    private String createdAt;
    @Schema(title = "좋아요 수")
    private int feedLike;
    @Schema(title = "조회 수")
    private int viewCount;
}