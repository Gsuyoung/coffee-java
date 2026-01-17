package com.cafe.coffeejava.alarm.model;

import lombok.Getter;

@Getter
public enum AlarmType {
    COMMENT("댓글"),
    LIKE("좋아요"),
    REPORT("신고");

    private final String description;

    AlarmType(String description) {
        this.description = description;
    }
}