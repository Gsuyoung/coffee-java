package com.cafe.coffeejava.user.account;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
@RequiredArgsConstructor
public class EmailAuthScheduler {
    private final UserAccountMapper userAccountMapper;

    // 만료된 인증 코드 사용 처리
    @Transactional
    @Scheduled(cron = "0 * * * * *") // 매 분 0초마다 실행
    public void updExpiredCodeAsUsed() {
        int updatedCount = userAccountMapper.updExpiredCodeAsUsed();
        log.info("만료된 이메일 인증 코드 처리 완료: {}건",  updatedCount);
    }
}
