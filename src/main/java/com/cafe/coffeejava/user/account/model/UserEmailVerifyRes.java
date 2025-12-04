package com.cafe.coffeejava.user.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailVerifyRes {
    private String email;
    private long userId;
}
