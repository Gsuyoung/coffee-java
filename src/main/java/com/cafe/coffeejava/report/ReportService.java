package com.cafe.coffeejava.report;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.config.jwt.JwtUser;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.report.model.ReportCommentGetRes;
import com.cafe.coffeejava.report.model.ReportFeedGetRes;
import com.cafe.coffeejava.report.model.ReportPostReq;
import com.cafe.coffeejava.report.model.ReportTypeGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;
    private final AuthenticationFacade authenticationFacade;

    // 신고 등록
    @Transactional
    public int postReport(ReportPostReq req) {
        Long loginUserId = authenticationFacade.getSignedUserId();

        if ((req.getFeedId() == null && req.getCommentId() == null) || (req.getFeedId() != null && req.getCommentId() != null)) {
            throw new CustomException("게시글 또는 댓글 중 하나만 신고해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        if (req.getFeedId() != null) {
            if (!reportMapper.selFeedByFeedId(req.getFeedId())) {
                throw new CustomException("존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND);
            }
            if (reportMapper.selDistrictReportByFeedId(loginUserId, req.getFeedId())) {
                throw new CustomException("이미 신고한 게시글입니다.", HttpStatus.CONFLICT);
            }
        }

        if (req.getCommentId() != null) {
            if (!reportMapper.selCommentByCommentId(req.getCommentId())) {
                throw new CustomException("존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND);
            }
            if (reportMapper.selDistrictReportByCommentId(loginUserId, req.getCommentId())) {
                throw new CustomException("이미 신고한 댓글입니다.", HttpStatus.CONFLICT);
            }
        }

        int result =  reportMapper.insReport(loginUserId, req);

        return result;
    }

    // 신고 유형 불러오기 (select box 용)
    public List<ReportTypeGetRes> getReportType() {
        List<ReportTypeGetRes> list = reportMapper.selReportType();

        return list;
    }

    // 신고 당한 게시글 목록 불러오기 (관리자 전용)
    public List<ReportFeedGetRes> getReportFeed(Paging p) {
        JwtUser loginUser = authenticationFacade.getSignedUser();

        if (loginUser == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        if (loginUser.getRoles() != 1) { // 1= ROLE_ADMIN
            throw new CustomException("관리자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        List<ReportFeedGetRes> list = reportMapper.selReportFeedList(p);

        return list;
    }

    // 신고 당한 댓글 목록 불러오기 (관리자 전용)
    public List<ReportCommentGetRes> getReportComment(Paging p) {
        JwtUser loginUser = authenticationFacade.getSignedUser();

        if (loginUser == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        if (loginUser.getRoles() != 1) { // 1= ROLE_ADMIN
            throw new CustomException("관리자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        List<ReportCommentGetRes> list = reportMapper.selReportCommentList(p);

        return list;
    }

    // 신고 읽음 처리
    public int patchReportRead(long reportId) {
        JwtUser loginUser = authenticationFacade.getSignedUser();

        if (loginUser == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        if (loginUser.getRoles() != 1) { // 1= ROLE_ADMIN
            throw new CustomException("관리자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        int result = reportMapper.updReportRead(reportId);

        return result;
    }

    // 신고 게시글 or 댓글 제재 여부 변경
    @Transactional
    public int patchReportAction(long reportId, String actionReason) {
        JwtUser loginUser = authenticationFacade.getSignedUser();

        if (loginUser == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        if (loginUser.getRoles() != 1) { // 1= ROLE_ADMIN
            throw new CustomException("관리자만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        int result;

        if (StringUtils.hasText(actionReason)) {
            result = reportMapper.updReportWithAllAction(reportId, actionReason);
        } else  {
            result = reportMapper.updReportWithoutAction(reportId);
        }

        if (result == 0) {
            throw new CustomException("이미 처리되었거나 유효하지 않은 신고입니다.", HttpStatus.NOT_FOUND);
        }

        return result;
    }

    // 신고 취소
    public int patchReportCancel(long reportId) {
        JwtUser loginUser = authenticationFacade.getSignedUser();

        if (loginUser == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        if (loginUser.getRoles() != 0) { // 0 = ROLE_USER
            throw new CustomException("유저만 접근할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        int result = reportMapper.updReportCancel(reportId, loginUser.getSignedUserId());

        if (result == 0) {
            throw new CustomException("신고가 없거나 취소할 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        return result;
    }
}
