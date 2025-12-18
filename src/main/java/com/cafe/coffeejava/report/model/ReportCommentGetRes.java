package com.cafe.coffeejava.report.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportCommentGetRes {
    private long reportId;
    private long reporterId;
    private long reportTypeId;
    private long commentId;
    private int reportStatus;
    private int actionStatus;
    private LocalDateTime createdAt;
}
