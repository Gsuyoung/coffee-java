package com.cafe.coffeejava.user.account;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.common.model.EmailSender;
import com.cafe.coffeejava.common.model.EmailVerifyCodeGenerator;
import com.cafe.coffeejava.config.CookieUtils;
import com.cafe.coffeejava.config.constant.JwtConst;
import com.cafe.coffeejava.config.jwt.JwtTokenProvider;
import com.cafe.coffeejava.config.jwt.JwtUser;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.user.account.model.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountMapper userAccountMapper;
    private final JwtConst jwtConst;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtils  cookieUtils;
    private final AuthenticationFacade  authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerifyCodeGenerator emailVerifyCodeGenerator;
    private final EmailSender emailSender;

    // 회원가입
    public int postUser(UserSignUpReq req) {
        req.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));

        if(userAccountMapper.countByEmail(req.getEmail()) > 0) {
            throw new CustomException("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        if (userAccountMapper.countByNickname(req.getNickname()) > 0) {
            throw new CustomException("이미 사용중인 닉네임입니다.",  HttpStatus.BAD_REQUEST);
        }

        return userAccountMapper.insUser(req);
    }

    // 로그인
    @Transactional
    public UserSignInRes loginUser(UserSignInReq req, boolean isAdminPage, HttpServletResponse response) {
        UserSignInRes res = userAccountMapper.selUserByEmail(req.getEmail());

        if (res == null || !BCrypt.checkpw(req.getPassword(), res.getPassword())) {
            throw new CustomException("아이디 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (res.getUserStatus() == 1) {
            throw new CustomException("비활성화 된 계정입니다.", HttpStatus.FORBIDDEN);
        }

        // 유저 로그인인지 관리자 로그인인지 구분
        if (isAdminPage && res.getRole() != 1) {
            throw new CustomException("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);
        } else if (!isAdminPage && res.getRole() != 0) {
            throw new CustomException("유저 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        JwtUser jwtUser = new JwtUser();

        jwtUser.setSignedUserId(res.getUserId());
        jwtUser.setRoles(res.getRole());

        String accessToken = jwtTokenProvider.generateToken(jwtUser, jwtConst.getAccessTokenExpiry());
        String refreshToken = jwtTokenProvider.generateToken(jwtUser, jwtConst.getRefreshTokenExpiry());

        cookieUtils.setCookie(response, jwtConst.getRefreshTokenCookieName(), refreshToken, jwtConst.getRefreshTokenCookieExpiry());
        res.setAccessToken(accessToken);

        return res;
    }

    // 유저 탈퇴
    public int patchUser(UserPatchReq req) {
        Long loginUserId = authenticationFacade.getSignedUserId();

        if (!loginUserId.equals(req.getUserId())) {
            throw new CustomException("계정 탈퇴 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 유저 정보 조회
        UserPatchRes userInfo = userAccountMapper.selUserInfoByUserId(req.getUserId());

        // 비밀번호 확인
        if (!passwordEncoder.matches(req.getPassword(), userInfo.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return userAccountMapper.updateUser(req);
    }

    // 유저 이메일 인증 코드 발송
    @Transactional
    public String postEmailVerifyCode(String email) {
        // 이메일 조회
        UserEmailVerifyRes res = userAccountMapper.selUserEmailByEmail(email);
        log.info("DB 조회 결과 res: {}", res);
        log.info("userId: {}", res.getUserId());

        // 인증 코드 생성
        String code = emailVerifyCodeGenerator.generate6DigitCode();

        if (res != null && res.getEmail() != null) {
            // 이전 코드 처리: 가장 최근 코드 제외하고 나머지 is_used = 1
            userAccountMapper.updOldCodesAsUsed(res.getUserId());

            // 새로운 인증 코드 생성
            userAccountMapper.insEmailAuth(email, code);
            try {
                emailSender.sendEmail(
                        email,
                        "비밀번호 재설정 인증코드",
                        "인증코드: " + code
                );
            } catch (Exception e) {
                // 예외 발생 시 로그 기록
                log.error("메일 발송 실패: {}", email, e);
                // 필요 시 사용자에게 안내 메시지 변경 가능
                return "인증코드 발송 시 문제가 발생했습니다. 잠시 후 다시 시도해주세요.";
            }
        }

        return "인증코드가 발송되었습니다.";
    }

    // 유저 비밀번호 찾기
    @Transactional
    public int findPassword(UserFindPasswordReq req) {
        // 유저의 인증 코드 조회
        UserGetCodeRes res = userAccountMapper.selAuthCode(req.getUserId());

        if (res.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException("인증 코드가 만료되었습니다.",  HttpStatus.BAD_REQUEST);
        }

        if (!res.getCode().equals(req.getCode())) {
            throw new CustomException("인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 유저의 인증 코드가 일치하는 경우 사용 처리
        userAccountMapper.updOldCodesAsUsed(req.getUserId());


        // 비밀번호 조회
        UserPatchRes userPassword = userAccountMapper.selUserInfoByUserId(req.getUserId());

        // 새 비밀번호가 기존 비밀번호와 같다면 예외
        if (passwordEncoder.matches(req.getPassword(), userPassword.getPassword())) {
            throw new CustomException("현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 비밀번호 재설정
        String hashedPassword = passwordEncoder.encode(req.getPassword());
        req.setPassword(hashedPassword);
        int result = userAccountMapper.patchFindPassword(req);

        return result;
    }
}
