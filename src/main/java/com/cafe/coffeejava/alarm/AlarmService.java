package com.cafe.coffeejava.alarm;

import com.cafe.coffeejava.alarm.model.AlarmGetRes;
import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmMapper alarmMapper;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public List<AlarmGetRes> getAlarms(Paging p) {
        long userId = authenticationFacade.getSignedUserId();
        List<AlarmGetRes> result = alarmMapper.getAlarm(userId, p);
        return result;
    }

    public int updAlarmRead(Long alarmId) {
        long userId = authenticationFacade.getSignedUserId();

        int result = alarmMapper.updAlarmRead(userId, alarmId);
        return result;
    }
}