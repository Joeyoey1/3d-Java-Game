package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Chat;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;
import com.barbarian.game.network.ChatNetwork;
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
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class ChatSystem extends IteratingSystem{
    private ComponentMapper<ChatComponent> chat_m_ = ComponentMapper.getFor(ChatComponent.class);

    public static List<String> chatLog = new ArrayList<>();
    private static String msg = "";
    private ChatNetwork chatN_;
    private Ticker ticker = new Ticker();


    public ChatSystem (ChatNetwork cn) {
        super(Family.all(ChatComponent.class).get());
        chatN_ = cn;
        ticker.start();
        chatLog.add("");
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        final ChatComponent chat_c = chat_m_.get(entity);
        //StringBuilder msg = new StringBuilder(80);

        if(chat_c.getID() == Game.thisID) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text){
                        msg = text;
                        chat_c.setChatMessage(msg);
                    }
                    @Override
                    public void canceled(){
                    }
                }, "Message to send", "", "");
            }
            if (ticker.get_ticks(deltaTime) > 1 && chat_c.messageToSend()) {
                System.out.println("This player, " + chat_c.getID() + ": " + msg);
                chatN_.sendChatUpdate(chat_c.getChat());
                chat_c.clearMessage();
                ticker.start();
            }
        }
    }

}
