package com.cafe.coffeejava.user.account;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.user.account.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

import static reactor.netty.http.HttpConnectionLiveness.log;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="유저", description = "유저 관련 API")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/sign-up")
    @Operation(summary = "유저 회원가입")
    public ResultResponse<Integer> postUser(@RequestBody UserSignUpReq req) {
        int result = userAccountService.postUser(req);
        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                             .resultMsg("회원가입 완료")
                             .resultData(result)
                             .build();
    }

    @PostMapping("/sign-in")
    @Operation(summary = "유저 로그인")
    public ResultResponse<UserSignInRes> loginUser(@RequestBody UserSignInReq req, HttpServletResponse response) {
        UserSignInRes res =  userAccountService.loginUser(req, response);
        return ResultResponse.<UserSignInRes>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("로그인 성공")
                             .resultData(res)
                             .build();
    }

    @PatchMapping
    @Operation(summary = "유저 계정 탈퇴")
    public ResultResponse<Integer> patchUser(@RequestBody UserPatchReq req) {
        int result =  userAccountService.patchUser(req);
        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("계정 탈퇴 성공")
                             .resultData(result)
                             .build();
    }

    @PostMapping("/email-auth")
    @Operation(summary = "유저 인증 코드 발송")
    public ResultResponse<String> postEmailVerifyCode(@RequestBody UserEmailVerifyReq req) {
        String email = req.getEmail().replace("\"", "").trim();

        log.info("요청 email: '{}'", req.getEmail());
        String code = userAccountService.postEmailVerifyCode(email);
        return ResultResponse.<String>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                             .resultMsg("인증코드 발송 성공")
                             .resultData(code)
                             .build();
    }

}
