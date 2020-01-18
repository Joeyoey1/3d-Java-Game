package com.barbarian.game.network;

import com.badlogic.ashley.core.ComponentMapper;
import com.barbarian.game.components.ChatComponent;
import com.barbarian.game.utils.Ticker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class ChatNetwork implements Runnable{
    private ComponentMapper<ChatComponent> chat_m_ = ComponentMapper.getFor(ChatComponent.class);


    private static final ObjectMapper mapper = new ObjectMapper();
    private final ScheduledExecutorService schedule = newSingleThreadScheduledExecutor();

    private static WebSocketSession webSocketSession;

    private Thread t;
    public static List<String> chatLog = new ArrayList<>();
    private static String msg = "";
    private com.barbarian.game.models.Chat chat;
    private Ticker ticker = new Ticker();


    public ChatNetwork() {
        this.start();
        chatLog.add("");
    }

    @Override
    public void run() {
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "chatUpdate");
            t.setName("chatUpdate");
            t.start();
        }
        connect();
    }

    public void connect() {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            TextMessage message = new TextMessage(mapper.writeValueAsString(new com.barbarian.game.models.Chat()));
            webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    //LOGGER.info("received message - " + message.getPayload());
                    //System.out.println("received message - " + message.getPayload());
                    //if((message.getPayload().contains("health"))){
                    try {
                        //System.out.println("entered try");
                        chat = mapper.readValue(message.getPayload(), new TypeReference<com.barbarian.game.models.Chat>() {});
                        //if(chat.getId() != Game.thisID){
                        //System.out.println(chat.getId() + ": " + chat.getMessage());
                        chatLog.add(chat.getId() + ": " + chat.getMessage());
                        msg = chat.getId() + ": " + chat.getMessage();
                        //}
                        //System.out.println("num players: " + playerChat.size());
//                        for (Chat chat : playerChat) {
//                            if(chat == null){}
//                            else if(Game.players.get(chat.getId()) == null){
//                            }
//                           else if(chat.getId() != Game.thisID){
//                                System.out.println(chat.getId() + ": " + chat.getMessage());
//                            }
//                        }
                    }
                    catch (IOException e) {
                        //System.out.println("no other chats");
                    }
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    //LOGGER.info("established connection - " + session);
                    System.out.println("established connection - " + session);
                }
                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
                    System.out.println("" + closeStatus);
                }

            }, new WebSocketHttpHeaders(), URI.create("ws://coms-309-jr-4.misc.iastate.edu:8080/chat")).get();
        } catch (Exception exception) {
        }
    }

    public static void sendChatUpdate(com.barbarian.game.models.Chat chat) {
        if(webSocketSession == null) System.out.println("no connection");
        else if (webSocketSession.isOpen()) {
            try {
                TextMessage message = new TextMessage(mapper.writeValueAsString(chat));
                //System.out.println("sent: " + message.getPayload());
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("chat socket closed");
            //TODO TERMINATE IF WEBSOCKET DOESNT EXIST.
        }
    }

    public static String getLatestMessage(){
        if(msg != ""){
            return msg;
        }
        return "";
    }
}

