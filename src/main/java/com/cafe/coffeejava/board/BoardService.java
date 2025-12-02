package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.BoardPostReq;
import com.cafe.coffeejava.board.model.BoardPutReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public int postBoard(BoardPostReq p) {
        try {
            int result = boardMapper.insBoard(p);
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("공지사항 등록 실패");
        }
    }

    public int updBoard(BoardPutReq p) {
        try {
            int result = boardMapper.updBoard(p);
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("공지사항 수정 실패");
        }
    }
}