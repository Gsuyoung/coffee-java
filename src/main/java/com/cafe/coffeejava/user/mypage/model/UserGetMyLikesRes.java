package com.cafe.coffeejava.user.mypage.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserGetMyLikesRes {
    private long feedId;
    private String title;
    private int viewCount;
    private int likeCount;
    private LocalDateTime createdAt;
}
