package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.feed.model.FeedPicDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicMapper {
    int insFeedPic(FeedPicDto p);
    Long findFeedIdByFeedPic(Long feedPicId);
    String findPicNameByFeedPicId(Long feedPicId);
    int delFeedPic(Long feedPicId);
    List<Long> delFeedPicList(List<Long> feedPicId);
}