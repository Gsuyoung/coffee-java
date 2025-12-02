package com.cafe.coffeejava.user.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 로그인 정보")
public class UserSignInReq {
    @Schema(description = "아이디", example = "aaa@aaa.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Schema(description = "비밀번호", example = "123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
