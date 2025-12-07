package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.feed.model.FeedGetDetailDto;
import com.cafe.coffeejava.feed.model.FeedGetDto;
import com.cafe.coffeejava.feed.model.FeedPostReq;
import com.cafe.coffeejava.feed.model.FeedPutReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    List<FeedGetDto> getFeedList();
    List<FeedGetDto> getFeedListByDistrict(Long districtId);
    List<FeedGetDetailDto> getFeedDetailList(Long feedId);
    int updViewCount(Long feedId);
    int updFeed(FeedPutReq p);
    Long findUserIdByFeedId(Long feedId);
    int delFeed(Long feedId, Long userId);
}