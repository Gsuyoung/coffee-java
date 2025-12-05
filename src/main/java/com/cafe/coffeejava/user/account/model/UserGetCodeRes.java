package com.cafe.coffeejava.user.account.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserGetCodeRes {
    private long userId;
    private String code;
    private LocalDateTime expiresAt;
}
