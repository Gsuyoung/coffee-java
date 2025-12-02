package com.cafe.coffeejava.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode // Equals, HashCode 메소드 오버라이딩 > Test 용도
public class JwtUser {
    private Long signedUserId;
    private String roles; //인가(권한)처리 때 사용, ROLE_이름, ROLE_USER, ROLE_ADMIN
}