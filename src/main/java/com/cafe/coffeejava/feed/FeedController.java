package com.cafe.coffeejava.feed;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글 관리", description = "게시글 등록, 불러오기, 수정, 삭제")
@Builder
@RestController
@RequestMapping("feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
}