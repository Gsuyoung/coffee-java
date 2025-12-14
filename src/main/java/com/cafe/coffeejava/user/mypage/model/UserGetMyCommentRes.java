package com.cafe.coffeejava.user.mypage.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserGetMyCommentRes {
    private String title;
    private String feedComment;
    private LocalDateTime createdAt;
}
