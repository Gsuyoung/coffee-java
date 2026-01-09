package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.*;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;

    // 댓글 등록
    @Transactional
    public int postComment(CommentPostReq req) {
        long loginUserId = authenticationFacade.getSignedUserId();

        // 피드가 존재하는지 검색
        boolean existsFeed = commentMapper.existsFeed(req.getFeedId());

        if (!existsFeed) {
            throw new CustomException("존재하지 않는 피드입니다.", HttpStatus.BAD_REQUEST);
        }

        // 대댓글인 경우
        if (req.getParentCommentId() != null) {
            ParentCommentGetRes parent = commentMapper.selParentComment(req.getParentCommentId());

            // 부모 댓글 존재 여부
            if (parent == null) {
                throw new CustomException("부모 댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
            }

            // 같은 피드인지 검증
            if (parent.getFeedId() != req.getFeedId()) {
                throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
            }

            // 대댓글의 대댓글 방지
            if (parent.getParentCommentId() != null) {
                throw new CustomException("대댓글에는 답글을 달 수 없습니다.", HttpStatus.BAD_REQUEST);
            }

            // 부모 댓글 제재 상태
            if (parent.getActionStatus() == 1) {
                throw new CustomException("답글을 달 수 없는 댓글입니다.", HttpStatus.BAD_REQUEST);
            }
        }

        int result = commentMapper.insComment(loginUserId, req);

        return result;
    }

    // 댓글 조회
    public List<CommentGetRes> getComment(CommentGetReq req) {
        List<CommentGetRes> list = commentMapper.selComment(req);

        // 피드가 존재하는지 검색
        boolean existsFeed = commentMapper.existsFeed(req.getFeedId());

        if (!existsFeed) {
            throw new CustomException("존재하지 않는 피드입니다.", HttpStatus.BAD_REQUEST);
        }

        return list;
    }

    // 댓글 수정
    public int patchComment(CommentPatchReq req, long commentId) {
        long loginUserId = authenticationFacade.getSignedUserId();

        CommentGetUserIdRes userIdFromComment = commentMapper.selUserIdFromComment(commentId);

        if (userIdFromComment == null) {
            throw new CustomException("존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST);
        }

        if (userIdFromComment.getUserId() != loginUserId) {
            throw new CustomException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        CommentGetActionRes res = commentMapper.selActionStatus(commentId);
        if (res.getActionStatus() == 1) {
            throw new CustomException("수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        int result = commentMapper.updComment(commentId, loginUserId, req.getFeedComment());

        return result;
    }

    // 댓글 삭제
    public int deleteComment(long commentId) {
        long loginUserId = authenticationFacade.getSignedUserId();

        CommentGetUserIdRes userIdFromComment = commentMapper.selUserIdFromComment(commentId);

        if (userIdFromComment == null) {
            throw new CustomException("존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST);
        }

        if (userIdFromComment.getUserId() != loginUserId) {
            throw new CustomException("권한이 없는 요청입니다.", HttpStatus.FORBIDDEN);
        }

        CommentGetActionRes res = commentMapper.selActionStatus(commentId);
        if (res.getActionStatus() == 1) {
            throw new CustomException("삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        int result = commentMapper.delComment(commentId, loginUserId);

        return result;
    }
}
