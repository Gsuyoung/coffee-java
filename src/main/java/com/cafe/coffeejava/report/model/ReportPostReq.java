package com.cafe.coffeejava.report.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "신고 등록 정보")
public class ReportPostReq {
    @Schema(description = "신고 유형 pk", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long reportTypeId;
    @Schema(description = "게시글 pk", example = "1")
    private Long feedId;
    @Schema(description = "댓글 pk", example = "1")
    private Long commentId;
}
