package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    List<FeedGetDto> getFeedList(Paging p);
    List<FeedGetDto> getFeedListByDistrict(FeedDistrictGetReq p);
    FeedGetDetailDto getFeedDetailList(Long feedId);
    int updViewCount(Long feedId);
    //int findUserIdByFeed(Long feedId);
    int updFeed(FeedPutReq p);
    Long findUserIdByFeed(Long feedId);
    int existsBlockedFeed(Long feedId);
    int delFeed(Long feedId);

    List<PopularFeedGetRes> getPopularFeed();
}