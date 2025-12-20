package com.cafe.coffeejava.dashboard;

import com.cafe.coffeejava.dashboard.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashBoardMapper dashBoardMapper;

    public List<DashboardGetDistrictRes> getDistrictFeedCount(DashboardMonthReq p) {
        YearMonth ym = YearMonth.parse(p.getYearMonth());
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = startDate.plusMonths(1);
        return dashBoardMapper.getDistrictCount(startDate, endDate);
    }

//    public List<DashboardGetReportUserRes> getReportUserList() {
//        return dashBoardMapper.getReportUserList();
//    }

    public List<DashboardGetReportCount> getFeedReportCount(DashboardMonthReq p) {
        YearMonth ym = YearMonth.parse(p.getYearMonth());
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = startDate.plusMonths(1);
        return dashBoardMapper.getReportFeedCount(startDate, endDate);
    }

    public List<DashboardGetReportCount> getCommentReportCount(DashboardMonthReq p) {
        YearMonth ym = YearMonth.parse(p.getYearMonth());
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = startDate.plusMonths(1);
        return dashBoardMapper.getReportCommentCount(startDate, endDate);
    }

    public List<DashboardTopUserRes> getTopUserList(DashboardMonthReq p) {
        YearMonth ym = YearMonth.parse(p.getYearMonth());
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = startDate.plusMonths(1);
        return dashBoardMapper.getTopUser(startDate, endDate);
    }

    public List<DashboardGetFeedCountRes> getFeedCount() {
        return dashBoardMapper.getFeedCount();
    }

    public List<DashboardGetTopReportUsers> getTopReportUsers() {
        return dashBoardMapper.getTopReportUsers();
    }
}