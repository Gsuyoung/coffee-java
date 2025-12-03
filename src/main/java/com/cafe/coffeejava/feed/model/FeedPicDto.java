package com.cafe.coffeejava.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FeedPicDto {
    private Long feedId;
    private List<String> pics;
}