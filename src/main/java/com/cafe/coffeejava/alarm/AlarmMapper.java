package com.cafe.coffeejava.alarm;

import com.cafe.coffeejava.alarm.model.AlarmGetRes;
import com.cafe.coffeejava.alarm.model.AlarmPostReq;
import com.cafe.coffeejava.common.model.Paging;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmMapper {
    int insAlarm(@Param("userId") Long userId, @Param("p")AlarmPostReq p);
    //알림 리스트 조회
    List<AlarmGetRes> getAlarm(@Param("userId") Long userId,
                               @Param("p") Paging p);
    //안 읽은 알림
    int countUnreadAlarm(@Param("userId") Long userId);
    //읽었을 때 처리
    int updAlarmRead(@Param("userId") Long userId,
                     @Param("alarmId") Long alarmId);
}