package com.cafe.coffeejava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //클라이언트가 처음 접속하는 주소
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                //WebSocket 안 될 때 fallback
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트 → 서버
        registry.setApplicationDestinationPrefixes("/app");
        // topic : 서버 → 여러 명, queue : 서버 → 특정 유저
        registry.enableSimpleBroker("/topic", "/queue");
    }
}
