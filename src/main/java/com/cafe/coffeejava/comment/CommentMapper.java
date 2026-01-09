package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insComment(@Param("userId") long userId, @Param("req") CommentPostReq req);
    boolean existsFeed(@Param("feedId") long feedId);
    List<CommentFlatRes> selCommentsByParentIds(@Param("feedId") long feedId, @Param("parentIds") List<Long> parentIds);
    List<Long> selParentCommentIds(CommentGetReq req);
    int updComment(@Param("commentId") long commentId, @Param("userId")  long userId, @Param("feedComment") String feedComment);
    CommentGetUserIdRes selUserIdFromComment(long commentId);
    int delComment(@Param("commentId") long commentId,  @Param("userId") long userId);
    CommentGetActionRes selActionStatus(long commentId);
    ParentCommentGetRes selParentComment(@Param("parentCommentId") long parentCommentId);
}
