package com.cafe.coffeejava.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "댓글 등록 정보")
public class CommentPostReq {
    private long commentId;
    @Schema(description = "게시글 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Size(max = 100, message = "댓글은 100자 이내로 작성해주세요.")
    @Schema(description = "댓글 내용", example = "정말 가보고 싶은 카페네요!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String feedComment;
    @Schema(description = "부모 댓글 PK", example = "1")
    private Long parentCommentId;
}
