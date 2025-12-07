package com.cafe.coffeejava.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedGetDetailDto {
    private Long feedId;
    private String title;
    private String nickname;
    private String cafeName;
    private String createdAt;
    private String content;
    private int viewCount;
    private int feedLike;
    private List<String> pics;
}