package com.cafe.coffeejava.dashboard.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardGetFeedCountRes {
    private int year;
    private int month;
    private int feedCount;
}