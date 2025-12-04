package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.common.MyFileUtils;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.feed.model.FeedPicDto;
import com.cafe.coffeejava.feed.model.FeedPostReq;
import com.cafe.coffeejava.feed.model.FeedPostRes;
import com.cafe.coffeejava.feed.model.FeedPutReq;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Builder
@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final MyFileUtils myFileUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public FeedPostRes insFeed(List<MultipartFile> pics, FeedPostReq p) {
        feedMapper.insFeed(p);
        long feedId = p.getFeedId();

        String middlePath = String.format("feed/%d", feedId);
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>(pics.size());

        for(MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        feedPicDto.setPics(picNameList);
        feedPicMapper.insFeedPic(feedPicDto);

        Long signedUserId = authenticationFacade.getSignedUserId();
        if(p.getUserId() == null) {
            throw new CustomException("유저 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }

        if(!signedUserId.equals(p.getUserId())) {
            throw new CustomException("작성 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    public int updFeed(FeedPutReq p) {
        Long userId = authenticationFacade.getSignedUserId();
        if(p.getFeedId() == null) {
            throw new CustomException("해당 피드가 없습니다.", HttpStatus.NOT_FOUND);
        }
        if(p.getUserId() == null) {
            throw new CustomException("유저 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }
        if(!userId.equals(p.getUserId())) {
            throw new CustomException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        if(p.getDistrictId() == null) {
            throw new CustomException("행정구역 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }
        return feedMapper.updFeed(p);
    }

    public int delFeed(Long feedId, Long userId) {
        Long userIdByFeedId = feedMapper.findUserIdByFeedId(feedId);
        Long signedUserId = authenticationFacade.getSignedUserId();

        if(feedId == null) {
            throw new CustomException("해당 피드가 없습니다.", HttpStatus.NOT_FOUND);
        }

        if(!signedUserId.equals(userId)) {
            throw new CustomException("로그인된 사용자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        if(!userIdByFeedId.equals(userId)) {
            throw new CustomException("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        return feedMapper.delFeed(feedId, userId);
    }
}
