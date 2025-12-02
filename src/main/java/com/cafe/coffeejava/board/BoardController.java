package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.BoardPostReq;
import com.cafe.coffeejava.board.model.BoardPutReq;
import com.cafe.coffeejava.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항 관리", description = "공지사항 등록, 불러오기, 수정, 삭제")
@Builder
@RestController
@RequestMapping("Board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "공지사항 등록")
    public ResultResponse<Integer> postBoard(@RequestBody BoardPostReq p) {
        int result = boardService.postBoard(p);

        return ResultResponse.<Integer>builder()
                .resultMsg("공지사항 등록 완료")
                .resultData(result)
                .build();
    }


    @PutMapping
    @Operation(summary = "공지사항 수정")
    public ResultResponse<Integer> putBoard(@RequestBody BoardPutReq p) {
        int result = boardService.updBoard(p);

        return ResultResponse.<Integer>builder()
                .resultMsg("공지사항 수정 완료")
                .resultData(result)
                .build();
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "공지사항 삭제")
    public ResultResponse<Integer> delBoard(@PathVariable int boardId) {
        int result = boardService.delBoard(boardId);

        return ResultResponse.<Integer>builder()
                .resultMsg("공지사항 삭제 완료")
                .resultData(result)
                .build();
    }
}