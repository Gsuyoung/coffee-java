package com.cafe.coffeejava.alarm.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlarmPostReq {
    private AlarmType type;
    private Long targetId;
    private String message;
}