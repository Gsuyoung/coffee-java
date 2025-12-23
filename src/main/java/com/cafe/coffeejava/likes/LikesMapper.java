package com.cafe.coffeejava.likes;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikesMapper {
    int insLikes(@Param("userId") long userId, @Param("feedId") long feedId);
    int delLikes(@Param("userId") long userId, @Param("feedId") long feedId);
    boolean existsLikes(@Param("userId") long userId, @Param("feedId") long feedId);
    boolean existsFeed(@Param("feedId") long feedId);
    long selFeedWriter(long feedId);
}
