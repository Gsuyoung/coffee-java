package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.*;
import com.cafe.coffeejava.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "공지사항 관리", description = "공지사항 등록, 불러오기, 수정, 삭제")
@Builder
@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "공지사항 등록")
    public ResultResponse<Integer> postBoard(@RequestBody BoardPostReq p) {
        int result = boardService.postBoard(p);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_CREATED))
                .resultMsg("공지사항 등록 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "공지사항 목록 조회")
    public ResultResponse<List<BoardGetRes>> getBoard(@PathVariable Long userId) {
        List<BoardGetRes> result = boardService.getBoard(userId);

        return ResultResponse.<List<BoardGetRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("공지사항 목록 조회 완료")
                .resultData(result)
                .build();
    }

    @GetMapping("detail/{boardId}")
    @Operation(summary = "공지 세부사항 조회")
    public ResultResponse<List<BoardDetailGetRes>> getBoardDetail(@PathVariable Long boardId) {
        List<BoardDetailGetRes> result = boardService.getBoardDetail(boardId);

        return ResultResponse.<List<BoardDetailGetRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("공지 세부사항 조회 완료")
                .resultData(result)
                .build();
    }

    @PatchMapping
    @Operation(summary = "공지사항 수정")
    public ResultResponse<Integer> patchBoard(@RequestBody BoardPutReq p) {
        int result = boardService.patchBoard(p);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("공지사항 수정 완료")
                .resultData(result)
                .build();
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "공지사항 삭제")
    public ResultResponse<Integer> delBoard(@PathVariable Long boardId, @RequestParam Long userId) {
        int result = boardService.delBoard(boardId, userId);

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_NO_CONTENT))
                .resultMsg("공지사항 삭제 완료")
                .resultData(result)
                .build();
    }
}