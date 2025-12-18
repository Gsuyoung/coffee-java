package com.cafe.coffeejava.dashboard;

import com.cafe.coffeejava.dashboard.model.DashboardGetDistrictRes;
import com.cafe.coffeejava.dashboard.model.DashboardGetReportUserRes;
import com.cafe.coffeejava.dashboard.model.DashboardMonthReq;
import com.cafe.coffeejava.dashboard.model.DashboardTopUserRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DashBoardMapper {
    int getDistrictCount(Long districtId);
    List<DashboardGetReportUserRes> getReportUserList();
    List<DashboardTopUserRes> getTopUser(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);
}