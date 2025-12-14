package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.CommentGetRes;
import com.cafe.coffeejava.comment.model.CommentGetUserIdRes;
import com.cafe.coffeejava.comment.model.CommentPatchReq;
import com.cafe.coffeejava.comment.model.CommentPostReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insComment(@Param("userId") long userId, @Param("req") CommentPostReq req);
    boolean existsFeed(@Param("feedId") long feedId);
    List<CommentGetRes> selComment(@Param("feedId") long feedId);
    int updComment(@Param("commentId") long commentId, @Param("userId")  long userId, @Param("feedComment") String feedComment);
    CommentGetUserIdRes selUserIdFromComment(long commentId);
}
