package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.user.mypage.model.UserPatchPasswordReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Tag(name="마이 페이지", description = "마이 페이지 관련 API")
public class UserMyPageController {
    private final UserMyPageService userMyPageService;

    @PatchMapping("password")
    @Operation(summary = "비밀번호 변경")
    public ResultResponse<Integer> patchPassword(@RequestBody UserPatchPasswordReq req) {
        int result = userMyPageService.patchPassword(req);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("비밀번호 변경 성공")
                             .resultData(result)
                             .build();
    }
}
