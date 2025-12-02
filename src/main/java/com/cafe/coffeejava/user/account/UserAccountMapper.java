package com.cafe.coffeejava.user.account;

import com.cafe.coffeejava.user.account.model.UserSignInReq;
import com.cafe.coffeejava.user.account.model.UserSignInRes;
import com.cafe.coffeejava.user.account.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {
    int insUser(UserSignUpReq req);
    int countByEmail(String email);
    int countByNickname(String nickname);
    UserSignInRes selUserByEmail(String email);
}
