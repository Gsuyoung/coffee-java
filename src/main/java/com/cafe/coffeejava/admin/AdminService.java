package com.cafe.coffeejava.admin;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.jwt.JwtUser;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.user.account.model.UserSignInRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuthenticationFacade authenticationFacade;
    private AdminMapper adminMapper;

    public void blockUserByReport(Long reportId) {
        //로그인 유저인지 확인
        JwtUser isAdmin = authenticationFacade.getSignedUser();
        if(isAdmin.getSignedUserId() == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        //관리자인지 확인
        if (isAdmin.getRoles() != 1) {
            throw new CustomException("관리자 권한이 아닙니다.", HttpStatus.FORBIDDEN);
        }
        


    }
}
