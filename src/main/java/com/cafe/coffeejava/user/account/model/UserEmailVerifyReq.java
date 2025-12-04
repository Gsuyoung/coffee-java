package com.cafe.coffeejava.user.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 이메일 인증 정보")
public class UserEmailVerifyReq {
    @Schema(description = "유저 이메일", example = "aaa@aaa.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
