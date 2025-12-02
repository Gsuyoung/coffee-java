package com.cafe.coffeejava.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cafe.coffeejava.config.constant.JwtConst;
import com.cafe.coffeejava.config.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper;
    private final JwtConst jwtConst;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, JwtConst jwtConst) {
        this.objectMapper = objectMapper;
        this.jwtConst = jwtConst;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConst.getSecret()));
    }

    public String generateToken(JwtUser jwtUser, long expiredAt) {
        Date now = new Date();
        return makeToken(jwtUser, new Date(now.getTime() + expiredAt));
    }

    private String makeToken(JwtUser jwtUser, Date expiry) {
        // JWT 암호화
        // sgMemo: 밑에 빌더 패턴으로 생성되는 타입은 String(문자열)이다.
        return Jwts.builder()
                .header().type("JWT")
                .and()
                .issuer(jwtConst.getIssuer())
                .issuedAt(new Date())
                .expiration(expiry)
                .claim(jwtConst.getClaimKey(), makeClaimByUserToString(jwtUser))
                .signWith(secretKey)
                .compact();
    }

    //객체 > String : 직렬화(JSON)
    private String makeClaimByUserToString(JwtUser jwtUser) {
        //객체 자체를 JWT에 담고 싶어서 객체를 직렬화
        //jwtUser에 담고있는 데이터를 JSON형태의 문자열로 변환
        try {
            //objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);

        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public JwtUser getUser(String token) {
        Claims claims = getClaims(token);
        System.out.println((String)claims.get(jwtConst.getClaimKey()));
        String json = (String)claims.get(jwtConst.getClaimKey());
        JwtUser jwtUser = null;

        try {
            jwtUser = objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jwtUser;
    }

    public UserDetails getUserDetailsFromToken(String token) {
        JwtUser jwtUser = getUser(token);
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setJwtUser(jwtUser);

        return userDetails;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
