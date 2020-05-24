package com.pcbWeld.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket配置
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer{
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getMessageHandler(),"/websocket/*").addInterceptors(new MessageInterceptor());
    }

    @Bean
    public MessageHandler getMessageHandler(){
        return  new MessageHandler();
    }
}
