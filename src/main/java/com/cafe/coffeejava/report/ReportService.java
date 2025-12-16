package com.cafe.coffeejava.report;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.report.model.ReportPostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;
    private final AuthenticationFacade authenticationFacade;

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
}
