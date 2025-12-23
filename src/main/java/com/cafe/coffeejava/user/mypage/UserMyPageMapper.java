package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.user.mypage.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMyPageMapper {
    UserGetPasswordRes selUserPassword(long userId);
    int updPassword(UserPatchPasswordReq req);
    UserGetNicknameRes selUserNickname(long userId);
    int updNickname(UserPatchNicknameReq req);
    int isNicknameExist(String nickname);
    List<UserGetMyCommentRes> selUserMyComment(@Param("userId") long userId, @Param("p") Paging p);
    List<UserGetMyLikesRes> selUserMyLikesList(@Param("userId") long userId, @Param("p") Paging p);
    int updUserPic(@Param("userId") Long userId, @Param("picName") String picName);;

    List<UserGetMyFeedListRes> selUserMyFeedList(@Param("userId") long userId, @Param("p") Paging p);
}