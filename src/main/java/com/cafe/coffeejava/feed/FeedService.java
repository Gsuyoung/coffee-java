package com.cafe.coffeejava.feed;

import com.cafe.coffeejava.common.MyFileUtils;
import com.cafe.coffeejava.common.exception.CustomException;
import com.cafe.coffeejava.common.model.Paging;
import com.cafe.coffeejava.config.security.AuthenticationFacade;
import com.cafe.coffeejava.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    @Value("${file.directory}")
    private String fileDirectory;
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final MyFileUtils myFileUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public FeedPostRes insFeed(List<MultipartFile> pics, FeedPostReq p) {

        //req객체 mapper에 저장
        feedMapper.insFeed(p);
        //feedId 찾기
        long feedId = p.getFeedId();

        //사진이 있을 때만 폴더 생성 + 사진 저장 로직 실행
        List<String> picNameList = new ArrayList<>();
        if (pics != null && !pics.isEmpty()) {

            //중간 경로에 폴더 만들기
            String middlePath = String.format("feed/%d", feedId);
            myFileUtils.makeFolders(middlePath);

            //사진들 하나씩 돌면서 랜덤 이름 만들기
            for (MultipartFile pic : pics) {
                String savedPicName = myFileUtils.makeRandomFileName(pic);
                picNameList.add(savedPicName);

                //랜덤 이름 만들어진거 마지막 filePath에 저장
                String filePath = String.format("%s/%s", middlePath, savedPicName);
                try {
                    myFileUtils.transferTo(pic, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //FeedPic 저장
            FeedPicDto feedPicDto = new FeedPicDto();
            feedPicDto.setFeedId(feedId);
            feedPicDto.setPics(picNameList);
            feedPicMapper.insFeedPic(feedPicDto);
        }

        Long signedUserId = authenticationFacade.getSignedUserId();
        if (p.getUserId() == null) {
            throw new CustomException("유저 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }

        if (!signedUserId.equals(p.getUserId())) {
            throw new CustomException("작성 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    @Transactional
    public List<FeedGetDto> feedGetResList(Paging p) {
        List<FeedGetDto> result = feedMapper.getFeedList(p);
        return result;
    }

    @Transactional
    public List<FeedGetDto> feedGetListByDistrict(FeedDistrictGetReq p) {
        if (p.getDistrictId() == null) {
            throw new CustomException("잘못된 행정구역 ID 입니다.", HttpStatus.NOT_FOUND);
        }

        List<FeedGetDto> result = feedMapper.getFeedListByDistrict(p);
        return result;
    }

    @Transactional
    public FeedGetDetailDto feedGetDetail(Long feedId) {
        if (feedId == null) {
            throw new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        //조회수 증가
        int updated = feedMapper.updViewCount(feedId);
        if (updated == 0) {
            throw new CustomException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
//        Long signedUserId = authenticationFacade.getSignedUserId();
//        Long userId = feedMapper.findUserIdByFeedId(feedId);
//
//        //로그인 안한 경우 -> 조회 수 증가
//        if(signedUserId == null) {
//            feedMapper.updViewCount(feedId);
//        }
//
//        //로그인 한 경우 + 본인 글이 아닌 경우 -> 조회 수 증가
//        if(!signedUserId.equals(userId)) {
//            feedMapper.updViewCount(feedId);
//        }

        FeedGetDetailDto result = feedMapper.getFeedDetailList(feedId);
        return result;
    }

    @Transactional
    public int updFeed(FeedPutReq p) {
        Long signedUserId = authenticationFacade.getSignedUserId();
        if (p.getFeedId() == null) {
            throw new CustomException("해당 피드가 없습니다.", HttpStatus.NOT_FOUND);
        }
        if (p.getUserId() == null) {
            throw new CustomException("유저 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }
        if (!signedUserId.equals(p.getUserId())) {
            throw new CustomException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        if (p.getDistrictId() == null) {
            throw new CustomException("행정구역 ID가 없습니다.", HttpStatus.NOT_FOUND);
        }

        int result = feedMapper.existsBlockedFeed(p.getFeedId());
        if(result > 0) {
            throw new CustomException("해당 피드는 수정할 수 없습니다.", HttpStatus.FORBIDDEN);
        }

        return feedMapper.updFeed(p);
    }

    @Transactional
    public int delFeed(Long feedId) {

        if (feedId == null) {
            throw new CustomException("해당 피드가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Long signedUserId = authenticationFacade.getSignedUserId();
        Long userId = feedMapper.findUserIdByFeed(feedId);

        if(userId == null) {
            throw new CustomException("해당 피드를 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
        }

        //이거 authentication 구조상 현재는 필요없음.
//        if(signedUserId == null) {
//            throw new CustomException("로그인이 필요합니다.", HttpStatus.FORBIDDEN);
//        }

        if (!signedUserId.equals(userId)) {
            throw new CustomException("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        int result = feedMapper.existsBlockedFeed(feedId);
        if(result > 0) {
            throw new CustomException("해당 피드는 삭제할 수 없습니다.", HttpStatus.FORBIDDEN);
        }

        String baseDir = fileDirectory;
        String deletePath = String.format("%s/feed/%d", baseDir, feedId);
        myFileUtils.deleteFolder(deletePath, true);

        return feedMapper.delFeed(feedId);
    }

    @Transactional
    public int delFeedPic(List<Long> feedPicIds) {
        if (feedPicIds == null || feedPicIds.isEmpty()) {
            throw new CustomException("삭제할 사진이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        //사진들이 속한 feedId 조회(중복 제거)
        List<Long> feedIds = feedPicMapper.findFeedIdsByFeedPicIds(feedPicIds);

        if (feedIds.isEmpty()) {
            throw new CustomException("사진을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        if(feedIds.size() > 1) {
            throw new CustomException("서로 다른 게시글의 사진을 동시에 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Long feedId = feedIds.get(0);

        //권한 체크
        Long signedUserId = authenticationFacade.getSignedUserId();
        Long userId = feedMapper.findUserIdByFeed(feedId);

        if(userId == null) {
            throw new CustomException("해당 피드를 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
        }

        if (!signedUserId.equals(userId)) {
            throw new CustomException("사진 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        //삭제할 파일명 조회
        List<String> picNames = feedPicMapper.findPicNamesByFeedPicIds(feedPicIds);

        //파일 삭제
        String baseDir = fileDirectory;
        for(String picName : picNames) {
            String filePath = String.format("%s/feed/%d/%s", baseDir, feedId, picName);

            try {
                myFileUtils.deleteFile(filePath);
            } catch (IOException e) {
                throw new CustomException("파일 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //DB 삭제
        return feedPicMapper.delFeedPics(feedPicIds);
    }

    public List<PopularFeedGetRes> getPopularFeed() {
        return feedMapper.getPopularFeed();
    }
}