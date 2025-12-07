package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.user.mypage.model.UserGetPasswordRes;
import com.cafe.coffeejava.user.mypage.model.UserPatchPasswordReq;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserMyPageService {
    private final UserMyPageMapper userMyPageMapper;
    private final AuthenticationFacade authenticationFacade;

    // 마이 페이지 비밀번호 변경
    @Transactional
    public int patchPassword(UserPatchPasswordReq req) {
        Long loginUserId = authenticationFacade.getSignedUserId();

        if (!loginUserId.equals(req.getUserId())) {
            throw new CustomException("비밀번호 변경 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // DB에서 기존 비밀번호 조회
        UserGetPasswordRes res = userMyPageMapper.selUserPassword(req.getUserId());

        // 현재 비밀번호 검증
        if (!BCrypt.checkpw(req.getOldPassword(), res.getPassword())) {
            throw new CustomException("현재 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }


        if (req.getOldPassword().equals(req.getNewPassword())) {
            throw new CustomException("현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 비밀번호 변경
        String newEncodedUpw = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt());
        req.setNewPassword(newEncodedUpw);

        return userMyPageMapper.updPassword(req);
    }
}
