package com.cafe.coffeejava.admin;

import com.cafe.coffeejava.admin.model.AdminGetCountReportRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<AdminGetCountReportRes> getCountReportUser();
    int updUserStatus(Long userId);
}