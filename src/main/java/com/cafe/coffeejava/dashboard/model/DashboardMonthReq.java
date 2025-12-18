package com.cafe.coffeejava.dashboard.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardMonthReq {
    @Schema(title = "년,월", example = "2025-12")
    private String yearMonth;
}