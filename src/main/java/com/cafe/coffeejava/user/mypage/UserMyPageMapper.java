package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.user.mypage.model.UserGetPasswordRes;
import com.cafe.coffeejava.user.mypage.model.UserPatchPasswordReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMyPageMapper {
    UserGetPasswordRes selUserPassword(long userId);
    int updPassword(UserPatchPasswordReq req);
}
