package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.user.mypage.model.UserGetNicknameRes;
import com.cafe.coffeejava.user.mypage.model.UserGetPasswordRes;
import com.cafe.coffeejava.user.mypage.model.UserPatchNicknameReq;
import com.cafe.coffeejava.user.mypage.model.UserPatchPasswordReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMyPageMapper {
    UserGetPasswordRes selUserPassword(long userId);
    int updPassword(UserPatchPasswordReq req);
    UserGetNicknameRes selUserNickname(long userId);
    int updNickname(UserPatchNicknameReq req);
    int isNicknameExist(String nickname);
}
