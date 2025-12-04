package com.cafe.coffeejava.feed.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class FeedPostRes {
    private Long feedId;
    private List<String> pics;
}