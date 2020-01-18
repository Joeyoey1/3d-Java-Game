package com.jschirm.playerservice.websocket;

import com.jschirm.playerservice.model.Location;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    /**
     * @param webSocketHandlerRegistry
     * config websocket
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new GameSocketHandler(), "/socket").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(new ChatSocketHandler(), "/chat").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(new LobbySocketHandler(), "/lobby").setAllowedOrigins("*");
    }


    public static double getDistance(Location first, Location second) {
        if (first == null || second == null) return -1;
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2) + Math.pow(first.getZ() - second.getZ(), 2));
    }

}
