package com.cafe.coffeejava.board;

import com.cafe.coffeejava.board.model.*;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final AuthenticationFacade authenticationFacade;

    public int postBoard(BoardPostReq p) {
        Long userId = authenticationFacade.getSignedUserId();
            if(!userId.equals(p.getUserId())) {
                throw new CustomException("로그인 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
            }

            if(p.getRole() != 1) {
                throw new CustomException("작성 권한이 없습니다.", HttpStatus.BAD_REQUEST);
            }
            return boardMapper.insBoard(p);
    }

    public List<BoardGetRes> getBoard(Long userId) {
        if(userId == null) {
            throw new CustomException("유저 ID가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<BoardGetRes> boardList = boardMapper.getBoard(userId);

        if(boardList == null || boardList.isEmpty()) {
            throw new CustomException("해당 유저의 공지사항이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return boardList;
    }

    public List<BoardDetailGetRes> getBoardDetail(Long boardId) {
        if(boardId == null) {
            throw new CustomException("공지사항 ID가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<BoardDetailGetRes> boardDetailList = boardMapper.getBoardDetail(boardId);

        if(boardDetailList == null || boardDetailList.isEmpty()) {
            throw new CustomException("해당 공지사항이 없습니다.", HttpStatus.NOT_FOUND);
        }
        return boardDetailList;
    }

    public int patchBoard(BoardPutReq p) {
            Long userId = boardMapper.findUserIdByBoardId(p.getBoardId());

            if(p.getBoardId() == null) {
                throw new CustomException("공지사항이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }

            if(!userId.equals(p.getUserId())) {
                throw new CustomException("작성자만 수정할 수 있습니다.", HttpStatus.FORBIDDEN);
            }

            return boardMapper.patchBoard(p);
    }

    public int delBoard(BoardDelReq p) {
        Long userId = boardMapper.findUserIdByBoardId(p.getBoardId());

        if(p.getBoardId() == null) {
            throw new CustomException("공지사항이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        if(!userId.equals(p.getUserId())) {
            throw new CustomException("작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }
        return boardMapper.delBoard(p);
    }
}