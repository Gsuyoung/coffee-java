package com.cafe.coffeejava.comment.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentFlatRes {
    private long commentId;
    private long userId;
    private String nickname;
    private String feedComment;
    private Long parentCommentId;
    private LocalDateTime createdAt;
}
