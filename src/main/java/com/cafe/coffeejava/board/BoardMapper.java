package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insBoard(BoardPostReq p);
    Long roleByUserId(Long userId);
    List<BoardGetRes> getBoard(Long userId);
    List<BoardDetailGetRes> getBoardDetail(Long boardId);
    int patchBoard(BoardPutReq p);
    Long findUserIdByBoardId(Long boardId);
    int delBoard(Long boardId, Long userId);
}