package com.cafe.coffeejava.config.security;

import com.cafe.coffeejava.config.constant.JwtConst;
import com.cafe.coffeejava.config.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final JwtConst jwtConst;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        log.info("Request URI: {}", requestURI);;
        // 정적 리소스 예외 처리 (Spring Boot의 기본 정적 리소스 매핑 경로 반영)
        if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/images/") ||
                requestURI.equals("/index.html") || requestURI.equals("/")) {
            filterChain.doFilter(request, response);
            log.info("들어옴");
            return;
        }

        log.info("ip Address: {}", request.getRemoteAddr());
        String authorizeationHeader = request.getHeader(jwtConst.getHeaderKey()); // Bearer 토큰값
        log.info("authorizeationHeader: {}", authorizeationHeader);

        String token = getAccessToken(authorizeationHeader);
        log.info("token: {}", token);

        boolean result = false;

        if(token != null) {
            try {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                request.setAttribute("exception", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizaionHeader) {
        if(authorizaionHeader != null && authorizaionHeader.startsWith(jwtConst.getScheme())) {
            return authorizaionHeader.substring(jwtConst.getScheme().length() + 1);
        }

        return null;
    }
}
