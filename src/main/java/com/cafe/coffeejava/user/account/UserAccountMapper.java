package com.cafe.coffeejava.user.account;

import com.cafe.coffeejava.user.account.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {
    int insUser(UserSignUpReq req);
    int countByEmail(String email);
    int countByNickname(String nickname);
    UserSignInRes selUserByEmail(String email);
    int updateUser(UserPatchReq req);
    UserPatchRes selUserInfoByUserId(long userId);
    UserEmailVerifyRes selUserEmailByEmail(String email);
    int insEmailAuth(String email, String code);
    int updOldCodesAsUsed(long userId);
    int updExpiredCodeAsUsed();
    UserGetCodeRes selAuthCode(long userId);
    int patchFindPassword(UserPatchPasswordReq req);
}
