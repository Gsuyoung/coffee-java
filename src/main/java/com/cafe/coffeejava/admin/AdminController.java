package com.cafe.coffeejava.admin;

import com.cafe.coffeejava.admin.model.AdminGetCountReportRes;
import com.cafe.coffeejava.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/reportUserList")
    @Operation(summary = "일정 수 이상 신고당한 유저 리스트 조회")
    public ResultResponse<List<AdminGetCountReportRes>> getCountReportUser() {
        List<AdminGetCountReportRes> result = adminService.getCountReportUser();

        return ResultResponse.<List<AdminGetCountReportRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("5회이상 신고당한 유저 리스트 조회 완료")
                .resultData(result)
                .build();
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "비활성화 처리하기")
    public ResultResponse<Integer> patchUserStatus(@PathVariable Long userId) {
        int result = adminService.patchUserStatus(userId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("유저 비활성화 처리 완료")
                .resultData(result)
                .build();
    }
}