package com.cafe.coffeejava.comment.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentGetRes {
    private long commentId;
    private long userId;
    private String nickname;
    private String feedComment;
    private LocalDateTime createdAt;
}
