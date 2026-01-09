package com.cafe.coffeejava.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentCommentGetRes {
    private long commentId;
    private long feedId;
    private Long parentCommentId;
    private int actionStatus; // 0: 정상, 1: 제재
}
