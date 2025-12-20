package com.cafe.coffeejava.dashboard.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardGetTopReportUsers {
    private long userId;
    private String email;
    private String nickname;
    private int reportCount;
}