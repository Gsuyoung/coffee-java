package com.cafe.coffeejava.likes;

import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikesMapper likesMapper;
    private final AuthenticationFacade  authenticationFacade;

    // 좋아요 등록 & 삭제
    @Transactional
    public boolean LikesToggle(long feedId) {
        long loginUserId = authenticationFacade.getSignedUserId();

        // 피드 유무 조회
        boolean existsFeed = likesMapper.existsFeed(feedId);
        if (!existsFeed) {
            throw new CustomException("피드가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        long feedWritherId = likesMapper.selFeedWriter(feedId);
        if (loginUserId == feedWritherId) {
            throw new CustomException("본인 게시글에는 좋아요를 누를 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 좋아요 조회
        boolean existsLikes = likesMapper.existsLikes(loginUserId, feedId);

        // 좋아요가 있는 경우
        if (existsLikes) {
            // 좋아요 삭제
            likesMapper.delLikes(loginUserId, feedId);
            return false;
        } else {
            likesMapper.insLikes(loginUserId, feedId);
            return true;
        }
    }
}
