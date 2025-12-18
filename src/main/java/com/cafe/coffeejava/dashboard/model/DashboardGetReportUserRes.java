package com.cafe.coffeejava.dashboard.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardGetReportUserRes {
    @Schema(title = "유저 ID", example = "1")
    private long userId;
    @Schema(title = "신고 ID", example = "1")
    private long reportId;
    @Schema(title = "신고 대상 ID/ feed_id, comment_id", example = "1")
    private long targetId;
    @Schema(title = "신고 대상 유형", example = "Feed")
    private String targetType;
    @Schema(title = "신고 종류", example = "스팸/광고")
    private String reportTypeName;
    @Schema(title = "닉네임", example = "수엥이")
    private String nickName;
    @Schema(title = "제재 이유", example = "스팸 게시글입니다.")
    private String actionReason;
    @Schema(title = "신고 수", example = "1")
    private int reportCount;
}