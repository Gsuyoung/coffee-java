package com.cafe.coffeejava.dashboard;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.dashboard.model.DashboardGetReportUserRes;
import com.cafe.coffeejava.dashboard.model.DashboardMonthReq;
import com.cafe.coffeejava.dashboard.model.DashboardTopUserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "대시보드 관리", description = "대시보드 불러오기")
@Builder
@RestController
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/{districtId}")
    @Operation(summary = "행정구역별 게시글 수 조회")
    public ResultResponse<Integer> getDistrictFeedCount(@PathVariable Long districtId) {
        int result = dashboardService.getDistrictFeedCount(districtId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("행정구역별 게시글 수 조회 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("/reportUser")
    @Operation(summary = "신고 완료 처리된 유저 수 조회", description = "Feed/Comment 둘 중 하나만 뜹니다.")
    public ResultResponse<List<DashboardGetReportUserRes>> getReportUserList() {
        List<DashboardGetReportUserRes> result = dashboardService.getReportUserList();

        return ResultResponse.<List<DashboardGetReportUserRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("신고 완료 처리된 유저 수 조회 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("/topUser")
    @Operation(summary = "게시글 작성이 많은 유저 조회", description = "2025-12 형태로 입력해야합니다.")
    public ResultResponse<List<DashboardTopUserRes>> getTopUserList(@ModelAttribute @ParameterObject DashboardMonthReq p) {
        List<DashboardTopUserRes> result = dashboardService.getTopUserList(p);

        return ResultResponse.<List<DashboardTopUserRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("게시글 많은 유저 리스트 조회 완료")
                .resultData(result)
                .build();
    }
}