package com.cafe.coffeejava.report;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.report.model.ReportCommentGetRes;
import com.cafe.coffeejava.report.model.ReportFeedGetRes;
import com.cafe.coffeejava.report.model.ReportPostReq;
import com.cafe.coffeejava.report.model.ReportTypeGetRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name="신고", description = "신고 관련 API")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "신고 등록")
    public ResultResponse<Integer> postReport(@RequestBody ReportPostReq req) {
        int result =  reportService.postReport(req);

        return ResultResponse.<Integer>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                             .resultMsg("신고 등록 성공")
                             .resultData(result)
                             .build();
    }

    @GetMapping("/reportType")
    @Operation(summary = "신고 유형 불러오기")
    public ResultResponse<List<ReportTypeGetRes>> getReportType() {
        List<ReportTypeGetRes> list = reportService.getReportType();

        return ResultResponse.<List<ReportTypeGetRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("신고 유형 조회 성공")
                             .resultData(list)
                             .build();
    }

    @GetMapping("/feed")
    @Operation(summary = "신고 게시글 목록 조회")
    public ResultResponse<List<ReportFeedGetRes>> getReportFeed() {
        List<ReportFeedGetRes> list = reportService.getReportFeed();

        return ResultResponse.<List<ReportFeedGetRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("신고 게시글 목록 조회 성공")
                             .resultData(list)
                             .build();
    }

    @GetMapping("/comment")
    @Operation(summary = "신고 댓글 목록 조회")
    public ResultResponse<List<ReportCommentGetRes>> getReportComment() {
        List<ReportCommentGetRes> list = reportService.getReportComment();

        return ResultResponse.<List<ReportCommentGetRes>>builder()
                             .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                             .resultMsg("신고 댓글 목록 조회 성공")
                             .resultData(list)
                             .build();
    }
}
