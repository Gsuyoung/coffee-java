package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.CommentGetReq;
import com.cafe.coffeejava.comment.model.CommentGetRes;
import com.cafe.coffeejava.comment.model.CommentPatchReq;
import com.cafe.coffeejava.comment.model.CommentPostReq;
import com.cafe.coffeejava.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name="댓글", description = "댓글 관련 API")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 등록")
    public ResultResponse<Integer> postComment(@RequestBody @Valid CommentPostReq req) {
        int result =  commentService.postComment(req);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                             .resultMsg("댓글 등록 성공")
                             .resultData(result)
                             .build();
    }

    @GetMapping
    @Operation(summary = "댓글 조회")
    public ResultResponse<List<CommentGetRes>> getComment(@ModelAttribute @ParameterObject CommentGetReq req) {
        List<CommentGetRes> list = commentService.getComment(req);

        return ResultResponse.<List<CommentGetRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("댓글 조회 성공")
                             .resultData(list)
                             .build();

    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResultResponse<Integer> patchComment(@PathVariable long commentId, @RequestBody @Valid CommentPatchReq req) {
        int result =  commentService.patchComment(req, commentId);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("댓글 수정 성공")
                             .resultData(result)
                             .build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResultResponse<Integer> deleteComment(@PathVariable long commentId) {
        int result =  commentService.deleteComment(commentId);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("댓글 삭제 성공")
                             .resultData(result)
                             .build();
    }
}
