package com.cafe.coffeejava.user.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "비밀번호 찾기 유저 정보")
public class UserPatchPasswordReq {
    @Schema(description = "유저 PK", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;

    @Schema(description = "이메일 인증 코드", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "변경할 비밀번호", example = "1234!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
