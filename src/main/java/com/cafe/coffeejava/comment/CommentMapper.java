package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    //댓글 등록
    int insComment(@Param("userId") long userId, @Param("req") CommentPostReq req);
    //피드 존재 여부
    boolean existsFeed(@Param("feedId") long feedId);
    List<CommentFlatRes> selCommentsByParentIds(@Param("feedId") long feedId, @Param("parentIds") List<Long> parentIds);
    List<Long> selParentCommentIds(CommentGetReq req);
    int updComment(@Param("commentId") long commentId, @Param("userId")  long userId, @Param("feedComment") String feedComment);
    boolean existsReply(@Param("commentId") long commentId);
    CommentGetUserIdRes selUserIdFromComment(long commentId);
    int delComment(@Param("commentId") long commentId,  @Param("userId") long userId);
    CommentGetActionRes selActionStatus(long commentId);
    //부모 댓글 조회
    ParentCommentGetRes selParentComment(@Param("parentCommentId") long parentCommentId);

    Long findCommentOwnerId(long commentId);
}