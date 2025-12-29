package com.cafe.coffeejava.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminGetCountReportRes {
    private long userId;
    private String email;
    private String name;
    @Schema(title = "최근 신고일")
    private String recentReportAt;
    @Schema(title = "총 신고 수")
    private int totalReportCount;
}