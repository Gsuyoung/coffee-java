package com.cafe.coffeejava.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode // Equals, HashCode 메소드 오버라이딩 > Test 용도
public class JwtUser {
    private Long signedUserId;
    private int roles; //인가(권한)처리 때 사용, 0: ROLE_USER, 1: ROLE_ADMIN

    // 숫자 -> 문자열 권한 변환
    public String getRolesString() {
        return roles == 1 ? "ROLE_ADMIN" : "ROLE_USER";
    }
}