package com.cafe.coffeejava.report;

import com.cafe.coffeejava.report.model.ReportCommentGetRes;
import com.cafe.coffeejava.report.model.ReportFeedGetRes;
import com.cafe.coffeejava.report.model.ReportPostReq;
import com.cafe.coffeejava.report.model.ReportTypeGetRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    int insReport(@Param("reporterId") Long userId, @Param("req") ReportPostReq req);
    boolean selFeedByFeedId(Long feedId);
    boolean selCommentByCommentId(Long commentId);
    boolean selDistrictReportByFeedId(@Param("userId") Long userId, @Param("feedId") Long feedId);
    boolean selDistrictReportByCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);
    List<ReportTypeGetRes> selReportType();
    List<ReportFeedGetRes> selReportFeedList();
    List<ReportCommentGetRes> selReportCommentList();
}
