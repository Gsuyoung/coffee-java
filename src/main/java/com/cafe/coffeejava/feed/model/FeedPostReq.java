package com.cafe.coffeejava.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedPostReq {
    private Long userId;
    private Long districtId;
    private String title;
    private String cafeName;
    private String content;
}
