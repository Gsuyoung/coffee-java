package com.cafe.coffeejava.common.model;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EmailVerifyCodeGenerator {
    private final Random random = new Random();

    // 6자리 인증코드 생성
    public String generate6DigitCode() {
        int code = random.nextInt(1_000_000); // 0 ~ 999999
        return String.format("%06d", code);
    }
}
