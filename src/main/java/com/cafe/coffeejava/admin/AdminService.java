package com.cafe.coffeejava.admin;

import com.cafe.coffeejava.admin.model.AdminGetCountReportRes;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.jwt.JwtUser;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.user.account.model.UserSignInRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuthenticationFacade authenticationFacade;
    private final AdminMapper adminMapper;

    public List<AdminGetCountReportRes> getCountReportUser() {
        List<AdminGetCountReportRes> result = adminMapper.getCountReportUser();

        if(result == null || result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }

    @Transactional
    public int patchUserStatus(Long userId) {
        //유저 ID 확인
        if(userId == null) {
            throw new CustomException("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST);
        }
        //로그인 확인
        JwtUser isAdmin = authenticationFacade.getSignedUser();
        if(isAdmin.getSignedUserId() == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
        //관리자 권한 확인
        if (isAdmin.getRoles() != 1) {
            throw new CustomException("관리자 권한이 아닙니다.", HttpStatus.FORBIDDEN);
        }

        int result = adminMapper.updUserStatus(userId);

        if(result == 0) {
            throw new CustomException("이미 비활성화 처리되어있습니다.", HttpStatus.BAD_REQUEST);
        }
        return result;
    }
}