package com.cafe.coffeejava.config.websocket;

import com.cafe.coffeejava.config.constant.JwtConst;
import com.cafe.coffeejava.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtConst jwtConst;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader(jwtConst.getHeaderKey());

            if (authHeader != null && authHeader.startsWith(jwtConst.getScheme())) {
                String token = authHeader.substring(jwtConst.getScheme().length() + 1);

                Authentication authentication =
                        jwtTokenProvider.getAuthentication(token);

                accessor.setUser(authentication);
            }
        }
        return message;
    }
}