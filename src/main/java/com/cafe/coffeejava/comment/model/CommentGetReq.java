package com.cafe.coffeejava.comment.model;

import com.cafe.coffeejava.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "댓글 조회 정보")
public class CommentGetReq extends Paging {
    @Schema(description = "게시글 PK", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;

    public CommentGetReq(Integer page, Integer size) {
        super(page, size);
    }
}
