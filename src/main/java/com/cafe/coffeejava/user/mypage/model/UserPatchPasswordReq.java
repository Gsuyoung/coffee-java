package com.cafe.coffeejava.user.mypage.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 비밀번호 수정 정보")
public class UserPatchPasswordReq {
    @Schema(description = "유저 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(description = "유저 현재 비밀번호", example = "123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;
    @Schema(description = "유저 변경할 비밀번호", example = "1234!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
