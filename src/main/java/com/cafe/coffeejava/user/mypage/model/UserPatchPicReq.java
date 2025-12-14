package com.cafe.coffeejava.user.mypage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "유저 사진 수정 정보, delete = 삭제 유무")
public class UserPatchPicReq {
    @Schema(description = "유저 프로필 사진", example = "abc.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile pic;

    @JsonIgnore
    private String picName;
}
