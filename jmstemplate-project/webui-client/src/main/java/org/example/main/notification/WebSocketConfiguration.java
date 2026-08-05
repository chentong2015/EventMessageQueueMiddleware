package org.example.main.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${security.allowed-cors-origins}")
    private String[] allowedCorsOrigins;

    // 设置所有topic/**都由Broker管理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    // TODO. 当前端页面和SpringBoot应用不同源时，必须允许跨域请求
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notification")
                // .setAllowedOrigins(this.allowedCorsOrigins)
                // .setAllowedOriginPatterns("*")
                .setAllowedOriginPatterns("http://localhost:*")
                .withSockJS();
    }
}