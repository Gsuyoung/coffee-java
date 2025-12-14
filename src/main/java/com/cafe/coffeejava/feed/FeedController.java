package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글 관리", description = "게시글 등록, 불러오기, 수정, 삭제")
@Builder
@RestController
@RequestMapping("feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    @Operation(summary = "게시글 등록")
    public ResultResponse<FeedPostRes>insFeed(@RequestPart(required = false)
                                              List<MultipartFile> pics, @RequestPart FeedPostReq p) {
        FeedPostRes result = feedService.insFeed(pics,p);

        return ResultResponse.<FeedPostRes>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                .resultMsg("게시글 등록 성공")
                .resultData(result)
                .build();
    }

    @GetMapping()
    @Operation(summary = "게시글 목록 조회")
    public ResultResponse<List<FeedGetDto>> feedGetResList(@ModelAttribute @ParameterObject Paging p) {
        List<FeedGetDto> result = feedService.feedGetResList(p);

        return ResultResponse.<List<FeedGetDto>>builder()
                .statusCode((String.valueOf((HttpServletResponse.SC_OK))))
                .resultMsg("게시글 목록 조회 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("/district")
    @Operation(summary = "행정구역별 게시글 목록 조회")
    public ResultResponse<List<FeedGetDto>> getFeedListByDistrict(@ModelAttribute @ParameterObject FeedDistrictGetReq p) {
        List<FeedGetDto> result = feedService.feedGetListByDistrict(p);

        return ResultResponse.<List<FeedGetDto>>builder()
                .statusCode((String.valueOf(HttpServletResponse.SC_OK)))
                .resultMsg("행정구역별 게시글 목록 조회 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("/{feedId}")
    @Operation(summary = "게시글 세부사항 조회")
    public ResultResponse<FeedGetDetailDto> feedGetDetail (@PathVariable Long feedId) {
        FeedGetDetailDto result = feedService.feedGetDetail(feedId);

        return ResultResponse.<FeedGetDetailDto>builder()
                .statusCode((String.valueOf(HttpServletResponse.SC_OK)))
                .resultMsg("게시글 세부사항 조회 성공")
                .resultData(result)
                .build();
    }

    @PutMapping()
    @Operation(summary = "게시글 수정")
    public ResultResponse<Integer>updFeed(@RequestPart(value = "p") FeedPutReq p,
                                          @RequestPart(value = "pics", required = false) List<MultipartFile> pics,
                                          @RequestPart(value = "feedPicId", required = false) List<Long> feedPicId) {
        int result = feedService.updFeed(p, pics, feedPicId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("게시글 수정 성공")
                .resultData(result)
                .build();
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "게시글 삭제")
    public ResultResponse<Integer>delFeed(@PathVariable Long feedId) {
        int result = feedService.delFeed(feedId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_NO_CONTENT))
                .resultMsg("게시글 삭제 완료")
                .resultData(result)
                .build();
    }

    @DeleteMapping("feedPic/{feedPicId}")
    @Operation(summary = "게시글 사진 삭제")
    public ResultResponse<Integer>delFeedPic(@PathVariable Long feedPicId) {
        int result = feedService.delFeedPic(feedPicId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_NO_CONTENT))
                .resultMsg("게시글 사진 삭제 완료")
                .resultData(result)
                .build();
    }
}