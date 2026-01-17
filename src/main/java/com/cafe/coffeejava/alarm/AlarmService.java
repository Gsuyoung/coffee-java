package com.cafe.coffeejava.alarm;

import com.cafe.coffeejava.alarm.model.AlarmGetRes;
import com.cafe.coffeejava.alarm.model.AlarmPostReq;
import com.cafe.coffeejava.alarm.model.AlarmType;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmMapper alarmMapper;
    private final AuthenticationFacade authenticationFacade;
    private final SimpMessagingTemplate messagingTemplate;

    public void createCommentAlarm(long targetUserId, long commentId) {
        log.info("AlarmService 진입 targetUserId={}, commentId={}", targetUserId, commentId);

        AlarmPostReq p = new AlarmPostReq();
        p.setType(AlarmType.COMMENT);
        p.setTargetId(commentId);
        p.setMessage("댓글이 달렸습니다.");

        //DB 저장
        int result = alarmMapper.insAlarm(targetUserId, p);
        log.info("alarm insert 결과 = {}", result);

        //실시간 알림
        messagingTemplate.convertAndSendToUser(
                String.valueOf(targetUserId), //username
                "/queue/alarm", //destination
                p
        );
    }

    public void createLikeAlarm(long targetUserId, long feedId) {
        AlarmPostReq p = new AlarmPostReq();
        p.setType(AlarmType.LIKE);
        p.setTargetId(feedId);
        p.setMessage("게시글에 좋아요가 눌렸습니다.");

        alarmMapper.insAlarm(targetUserId, p);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(targetUserId),
                "/queue/alarm",
                p
        );
    }

    public void createReportAlarm(long targetUserId, long reportId) {
        AlarmPostReq p = new AlarmPostReq();
        p.setType(AlarmType.REPORT);
        p.setTargetId(reportId);
        p.setMessage("신고가 접수되었습니다.");

        alarmMapper.insAlarm(targetUserId, p);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(targetUserId),
                "/queue/alarm",
                p
        );
    }

    public List<AlarmGetRes> getAlarms(Paging p) {
        long userId = authenticationFacade.getSignedUserId();
        List<AlarmGetRes> result = alarmMapper.getAlarm(userId, p);
        return result;
    }

    public int countUnreadAlarm() {
        long userId = authenticationFacade.getSignedUserId();

        int result = alarmMapper.countUnreadAlarm(userId);
        return result;
    }

    public int updAlarmRead(Long alarmId) {
        long userId = authenticationFacade.getSignedUserId();

        int result = alarmMapper.updAlarmRead(userId, alarmId);
        if (result == 0) {
            throw new CustomException("이미 읽은 알림입니다.", HttpStatus.CONFLICT);
        }
        return result;
    }
}