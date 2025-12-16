package com.cafe.coffeejava.user.mypage;

import com.cafe.coffeejava.common.MyFileUtils;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.user.mypage.model.*;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMyPageService {
    private final UserMyPageMapper userMyPageMapper;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

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

    // 마이 페이지 닉네임 변경
    @Transactional
    public int patchUserNickname(UserPatchNicknameReq req) {
        Long loginUserId = authenticationFacade.getSignedUserId();

        if (!loginUserId.equals(req.getUserId())) {
            throw new CustomException("닉네임 변경 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // DB 유저 닉네임 조회
        UserGetNicknameRes res = userMyPageMapper.selUserNickname(req.getUserId());

        if (res.getNickname().equals(req.getNewNickname())) {
            throw new CustomException("현재 닉네임으로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // DB 닉네임 존재 유무 확인
        int count = userMyPageMapper.isNicknameExist(req.getNewNickname());

        if (count > 0) {
            throw new CustomException("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }

        return userMyPageMapper.updNickname(req);
    }

    // 마이 페이지 유저 댓글 조회
    public List<UserGetMyCommentRes> getMyComment() {
        Long loginUserId = authenticationFacade.getSignedUserId();

        return userMyPageMapper.selUserMyComment(loginUserId);
    }

    // 마이 페이지 좋아요 게시글 조회
    public List<UserGetMyLikesRes> getMyLike() {
        Long loginUserId = authenticationFacade.getSignedUserId();

        return userMyPageMapper.selUserMyLikesList(loginUserId);
    }

    @Transactional
    public String patchMyPic(UserPatchPicReq req) {
        Long loginUserId = authenticationFacade.getSignedUserId();
        MultipartFile file = req.getPic();

        // 프로필 사진 폴더 상대 경로
        String folderPath = String.format("user/%d", loginUserId);
        // 프로필 사진 폴더 절대 경로(삭제용)
        String absoluteUserFolder = myFileUtils.getUploadPath() + "/" + folderPath;

        // 기존 파일 삭제 (항상 수행)
        myFileUtils.deleteFolder(absoluteUserFolder, false);

        // 새 파일이 있으면 저장
        String savedPicName = null;

        if (file != null && !file.isEmpty()) {
            savedPicName = myFileUtils.makeRandomFileName(file);

            // 폴더 생성
            myFileUtils.makeFolders(folderPath);

            // 파일 저장
            String filePath = folderPath + "/" + savedPicName;
            try {
                myFileUtils.transferTo(file, filePath);
            } catch (IOException e) {
                throw new CustomException("프로필 사진 저장 실패", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // DB 업데이트 (파일명 or null)
        userMyPageMapper.updUserPic(loginUserId, savedPicName);

        return savedPicName;
    }

    public List<UserGetMyFeedListRes> getMyFeedList() {
        Long loginUserId = authenticationFacade.getSignedUserId();

        return userMyPageMapper.selUserMyFeedList(loginUserId);
    }
}