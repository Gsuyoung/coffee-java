package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.feed.model.FeedPicDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicMapper {
    int insFeedPic(FeedPicDto p);
    List<Long> findFeedIdsByFeedPicIds(List<Long> feedPicIds);
    List<String> findPicNamesByFeedPicIds(List<Long> feedPicIds);
    int delFeedPics(List<Long> feedPicIds);
}