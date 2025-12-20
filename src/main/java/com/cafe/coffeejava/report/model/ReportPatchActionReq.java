package com.cafe.coffeejava.report.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "제재 유무 정보")
public class ReportPatchActionReq {
    @Schema(description = "제재 사유", example = "광고성 게시물입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String actionReason;
}
