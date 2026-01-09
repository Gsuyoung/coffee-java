package com.cafe.coffeejava.alarm;

import com.cafe.coffeejava.alarm.model.AlarmGetRes;
import com.cafe.coffeejava.common.model.Paging;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmMapper {
    List<AlarmGetRes> getAlarm(@Param("userId") Long userId,
                               @Param("p") Paging p);
    int countUnreadAlarm(@Param("userId") Long userId);
    int updAlarmRead(@Param("userId") Long userId,
                     @Param("alarmId") Long alarmId);
}