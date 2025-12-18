package com.cafe.coffeejava.dashboard.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardTopUserRes {
    @Schema(title = "유저 ID", example = "1")
    private long userId;
    @Schema(title = "유저 이메일", example = "aaa@aaa.com")
    private String email;
    @Schema(title = "닉네임", example = "수엥")
    private String nickname;
    @Schema(title = "피드 개수", example = "1")
    private int feedCount;
}