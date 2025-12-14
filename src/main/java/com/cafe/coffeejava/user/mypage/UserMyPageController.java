package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.user.mypage.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Tag(name="마이 페이지", description = "마이 페이지 관련 API")
public class UserMyPageController {
    private final UserMyPageService userMyPageService;

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    public ResultResponse<Integer> patchPassword(@RequestBody UserPatchPasswordReq req) {
        int result = userMyPageService.patchPassword(req);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("비밀번호 변경 성공")
                             .resultData(result)
                             .build();
    }

    @PatchMapping("/nickname")
    @Operation(summary = "유저 닉네임 변경")
    public ResultResponse<Integer> patchNickname(@RequestBody UserPatchNicknameReq req) {
        int result = userMyPageService.patchUserNickname(req);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("닉네임 변경 성공")
                             .resultData(result)
                             .build();
    }

    @GetMapping("/comment")
    @Operation(summary = "유저 마이 페이지 댓글 조회")
    public ResultResponse<List<UserGetMyCommentRes>> getMyComment(@RequestParam long userId) {
        List<UserGetMyCommentRes> list = userMyPageService.getMyComment(userId);

        return ResultResponse.<List<UserGetMyCommentRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("댓글 조회 성공")
                             .resultData(list)
                             .build();
    }

    @GetMapping("/like")
    @Operation(summary = "유저 마이 페이지 좋아요 리스트 조회")
    public ResultResponse<List<UserGetMyLikesRes>> getMyLike(@RequestParam long userId) {
        List<UserGetMyLikesRes> list = userMyPageService.getMyLike(userId);

        return ResultResponse.<List<UserGetMyLikesRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("좋아요 누른 게시글 조회 성공")
                             .resultData(list)
                             .build();
    }

    @PatchMapping("/pic")
    @Operation(summary = "유저 사진 등록")
    public ResultResponse<String> patchMyPic(@ModelAttribute UserPatchPicReq req) {
        String pic = userMyPageService.patchMyPic(req);

        return ResultResponse.<String>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg(pic == null ? "유저 프로필 삭제 성공" : "유저 프로필 등록 성공")
                             .resultData(pic)
                             .build();
    }
}
