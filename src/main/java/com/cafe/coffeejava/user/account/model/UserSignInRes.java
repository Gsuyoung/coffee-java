package com.cafe.coffeejava.user.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인 응답")
public class UserSignInRes {
    private long userId;
    private String email;
    private String name;
    private String nickname;
    private String userPic;
    private int role;
    private int userStatus;
    private String accessToken;

    @JsonIgnore
    private String password;
}
