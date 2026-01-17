package com.cafe.coffeejava.notification;

import com.cafe.coffeejava.config.security.MyUserDetails;
import com.cafe.coffeejava.notification.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    // 프론트에서 /app/notify 로 보내면 여기로 들어옴
    @MessageMapping("/notify")
    public void send(NotificationMessage message,
                     Principal principal) {

        if(principal == null) {
            System.out.println("WebSocket principal null");
            return;
        }

        Long signedUserId = Long.valueOf(principal.getName());
        System.out.println("로그인 유저 ID: " + signedUserId);

        // 특정 유저에게만 보내기
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/notify/",
                message.getMessage()
        );
    }
}