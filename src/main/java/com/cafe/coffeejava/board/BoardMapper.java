package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.BoardDelReq;
import com.cafe.coffeejava.board.model.BoardPostReq;
import com.cafe.coffeejava.board.model.BoardPutReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int insBoard(BoardPostReq p);
    int updBoard(BoardPutReq p);
    int delBoard(int p);
}
