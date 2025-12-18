package com.cafe.coffeejava.dashboard;


import com.cafe.coffeejava.dashboard.model.DashboardGetReportUserRes;
import com.cafe.coffeejava.dashboard.model.DashboardMonthReq;
import com.cafe.coffeejava.dashboard.model.DashboardTopUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashBoardMapper dashBoardMapper;

    public int getDistrictFeedCount(Long districtId) {
        return dashBoardMapper.getDistrictCount(districtId);
    }

    public List<DashboardGetReportUserRes> getReportUserList() {
        return dashBoardMapper.getReportUserList();
    }

    public List<DashboardTopUserRes> getTopUserList(DashboardMonthReq p) {
        YearMonth ym = YearMonth.parse(p.getYearMonth());
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = startDate.plusMonths(1);
        return dashBoardMapper.getTopUser(startDate, endDate);
    }
}