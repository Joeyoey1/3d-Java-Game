package com.jschirm.playerservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jschirm.playerservice.model.ChatMessage;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.*;

public class ChatSocketHandler extends AbstractWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    private Set<WebSocketSession> connectedPlayers = new HashSet<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (message != null) {
            for (WebSocketSession webSocketSession : this.connectedPlayers) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connectedPlayers.add(session);
        for (WebSocketSession webSocketSession : this.connectedPlayers) {
            if (webSocketSession.getId() == session.getId()) {
                session.sendMessage(new TextMessage("You have connected!"));
            } else {
                webSocketSession.sendMessage(new TextMessage("New Player Connected."));
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connectedPlayers.remove(session);
        for (WebSocketSession webSocketSession : this.connectedPlayers) {
            webSocketSession.sendMessage(new TextMessage("Player Disconnected."));
        }
    }

}
