package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.feed.model.FeedPostReq;
import com.cafe.coffeejava.feed.model.FeedPutReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    int updFeed(FeedPutReq p);
    Long findUserIdByFeedId(Long feedId);
    int delFeed(Long feedId, Long userId);
}