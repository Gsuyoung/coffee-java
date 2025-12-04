package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.feed.model.FeedPicDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedPicMapper {
    int insFeedPic(FeedPicDto p);

}
