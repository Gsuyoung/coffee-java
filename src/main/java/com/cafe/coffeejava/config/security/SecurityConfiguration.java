package com.cafe.coffeejava.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 빈등록, 보통 빈등록 메소드가 포함되어 있을 가능성이 높다.
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //스프링이 메소드 호출을 하고 리턴한 객체의 주소값을 관리한다. (빈등록)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //시큐리티가 세션을 사용하지 않는다.
                .httpBasic(h -> h.disable()) //SSR(Server Side Rendering)이 아니다. 화면을 만들지 않을꺼기 때문에 비활성화 시킨다. 시큐리티 로그인창 나타나지 않을 것이다.
                .formLogin(form -> form.disable()) //SSR(Server Side Rendering)이 아니다. 폼로그인 기능 자체를 비활성화
                .csrf(csrf -> csrf.ignoringRequestMatchers("/ws/**")) //SSR(Server Side Rendering)이 아니다. 보안관련 SSR 이 아니면 보안이슈가 없기 때문에 기능을 끈다.
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(req -> req
                                .requestMatchers("/ws/**").permitAll()
                                        /*.requestMatchers("/api/user/sign-in"
                                        , "/api/reservation"
                                        , "/api/user/sign-up"
                                        , "/api/user/access-token"
                                        , "/api/admin/v3/sign-in"
                                        , "/api/admin/v3/sign-up"
                                        , "/api/admin/access-token"
                                        ,"api/admin/find-password"
                                        , "/api/restaurant"
                                        , "/api/restaurant/v3/main"
                                        , "/api/restaurant/v3/around"
                                        , "/api/restaurant/v3/detail"
                                        , "/api/user/find-id"
                                        , "/api/user/sign-up"
                                        , "api/user/find-password"
                                        , "/api/user/company/status"
                                        ,"/", "/favicon.ico", "/index.html", "/static/**", "/assets/**", "/css/**", "/js/**").permitAll() // 인증 없이 접근 허용
                                .requestMatchers("/api/user/**", "/api/restaurant/**").hasAnyRole("USER", "RESTAURANT")
                                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "RESTAURANT", "COMPANY")
                                .requestMatchers("/api/admin/**", "/api/restaurant/**", "/api/user/**").authenticated()
                                */.anyRequest().permitAll()
                )
                .build();
    }

}
