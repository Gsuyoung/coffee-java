package com.cafe.coffeejava.comment;

import com.cafe.coffeejava.comment.model.*;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
        // 피드가 존재하는지 검색
        boolean existsFeed = commentMapper.existsFeed(req.getFeedId());

        if (!existsFeed) {
            throw new CustomException("존재하지 않는 피드입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. 부모 댓글 ID만 페이징 조회
        List<Long> parentIds = commentMapper.selParentCommentIds(req);

        // 부모 댓글 자체가 없으면 바로 빈 리스트
        if (parentIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 3. 부모 + 대댓글 전체 조회
        List<CommentFlatRes> flatList = commentMapper.selCommentsByParentIds(req.getFeedId(), parentIds);

        // 4. 계층 구조 조립
        List<CommentGetRes> result = new ArrayList<>();
        CommentGetRes currentParent = null;

        for (CommentFlatRes flat : flatList) {

            CommentGetRes dto = new CommentGetRes();
            dto.setCommentId(flat.getCommentId());
            dto.setUserId(flat.getUserId());
            dto.setNickname(flat.getNickname());
            dto.setFeedComment(flat.getFeedComment());
            dto.setCreatedAt(flat.getCreatedAt());

            // 부모 댓글
            if (flat.getParentCommentId() == null) {
                currentParent = dto;
                result.add(dto);
            }
            // 대댓글
            else {
                // SQL 정렬 덕분에 항상 직전 부모가 맞음
                if (currentParent != null &&
                        currentParent.getCommentId() == flat.getParentCommentId()) {
                    currentParent.getChildren().add(dto);
                }
            }
        }

        return result;
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
        if (res != null && res.getActionStatus() == 1) {
            throw new CustomException("수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        boolean hasReply = commentMapper.existsReply(commentId);
        if (hasReply) {
            throw new CustomException("대댓글이 달린 댓글은 수정할 수 없습니다.", HttpStatus.BAD_REQUEST);
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
        if (res != null && res.getActionStatus() == 1) {
            throw new CustomException("삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        boolean hasReply = commentMapper.existsReply(commentId);
        if (hasReply) {
            throw new CustomException("대댓글이 달린 댓글은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        int result = commentMapper.delComment(commentId, loginUserId);

        return result;
    }
}
