package com.cafe.coffeejava.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SocketTestController {

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String test(String message) {
        log.info("받은 메시지: {}", message);
        return "서버 응답: " + message;
    }
}