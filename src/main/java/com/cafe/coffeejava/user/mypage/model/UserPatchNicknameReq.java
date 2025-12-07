package com.cafe.coffeejava.user.mypage.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 닉네임 수정 정보")
public class UserPatchNicknameReq {
    @Schema(description = "유저 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(description = "변경할 유저 닉네임", example = "킹어진", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newNickname;
}
