package com.cafe.coffeejava.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentGetActionRes {
    private long reportId;
    private long commentId;
    private int actionStatus;
}
