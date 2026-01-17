package com.cafe.coffeejava.alarm;

import com.cafe.coffeejava.alarm.model.AlarmGetRes;
import com.cafe.coffeejava.alarm.model.AlarmPostReq;
import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.common.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림")
@Builder
@RestController
@RequestMapping("alarms")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping
    @Operation(summary = "유저 알림 조회")
    public ResultResponse<List<AlarmGetRes>> getAlarms(@ModelAttribute @ParameterObject Paging p) {
        List<AlarmGetRes> result = alarmService.getAlarms(p);

        return ResultResponse.<List<AlarmGetRes>>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("알림 조회 성공")
                .resultData(result)
                .build();
    }

    @GetMapping("/unread")
    @Operation(summary = "안 읽은 알림 갯수 조회")
    public ResultResponse<Integer> countUnreadAlarm() {
        int result = alarmService.countUnreadAlarm();

        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("안 읽은 알림 갯수 조회 성공")
                .resultData(result)
                .build();
    }

    @PutMapping("/{alarmId}")
    @Operation(summary = "읽음 여부 수정")
    public ResultResponse<Integer> updAlarmsRead(@PathVariable Long alarmId) {
        Integer result = alarmService.updAlarmRead(alarmId);
        return ResultResponse.<Integer>builder()
                .statusCode(String.valueOf(HttpServletResponse.SC_OK))
                .resultMsg("읽음 여부 수정 성공")
                .resultData(result)
                .build();
    }
}