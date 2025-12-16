package com.cafe.coffeejava.report;

import com.cafe.coffeejava.report.model.ReportPostReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {
    int insReport(@Param("reporterId") Long userId, @Param("req") ReportPostReq req);
    boolean selFeedByFeedId(Long feedId);
    boolean selCommentByCommentId(Long commentId);
    boolean selDistrictReportByFeedId(@Param("userId") Long userId, @Param("feedId") Long feedId);
    boolean selDistrictReportByCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
