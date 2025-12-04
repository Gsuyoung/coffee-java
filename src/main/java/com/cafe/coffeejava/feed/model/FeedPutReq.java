package com.cafe.coffeejava.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedPutReq {
    @Schema(title = "피드 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long feedId;
    @Schema(title = "유저 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
    @Schema(title = "행정 구역 pk", example = "1")
    private Long districtId;
    @Schema(title = "제목", example = "공부하기 좋은 카페입니다.")
    private String title;
    @Schema(title = "카페 이름", example = "대봉정")
    private String cafeName;
    @Schema(title = "내용", example = "분위기 좋고 케이크가 저렴합니다.")
    private String content;
}