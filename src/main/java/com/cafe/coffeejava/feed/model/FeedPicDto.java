package com.cafe.coffeejava.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedPicDto {
    @Schema(title = "피드 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long feedId;
    @Schema(title = "피드 사진")
    private List<String> pics;
}