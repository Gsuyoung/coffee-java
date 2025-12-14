package com.cafe.coffeejava.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PopularFeedGetRes {
    private Long feedId;
    @JsonIgnore
    private Long districtId;
    @Schema(title = "행정 구역 이름", example = "중구")
    private String districtName;
    @Schema(title = "카페 이름", example = "대봉정")
    private String cafeName;
    @Schema(title = "제목", example = "공부하기 좋은 카페입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(title = "유저 닉네임", example = "나는홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
    @Schema(title = "작성일", example = "2025-12-07")
    private String createdAt;
    @Schema(title = "좋아요 수")
    private int feedLike;
}
