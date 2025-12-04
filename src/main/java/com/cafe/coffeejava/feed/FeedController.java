package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.common.model.ResultResponse;
import com.cafe.coffeejava.feed.model.FeedPostReq;
import com.cafe.coffeejava.feed.model.FeedPostRes;
import com.cafe.coffeejava.feed.model.FeedPutReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
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

    @PutMapping
    @Operation(summary = "게시글 수정")
    public ResultResponse<Integer>updFeed(@RequestBody FeedPutReq p) {
        int result = feedService.updFeed(p);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("게시글 수정 성공")
                .resultData(result)
                .build();
    }

    @DeleteMapping("/{feedId}")
    @Operation(summary = "게시글 삭제")
    public ResultResponse<Integer>delFeed(@PathVariable Long feedId, @RequestParam Long userId) {
        int result = feedService.delFeed(feedId, userId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_NO_CONTENT))
                .resultMsg("게시글 삭제 완료")
                .resultData(result)
                .build();
    }
}