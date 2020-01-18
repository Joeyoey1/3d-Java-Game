package com.jschirm.playerservice.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class LobbySocketHandler extends AbstractWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * All players connected, this includes players in lobbies and separate coliseums
     */
    private Map<WebSocketSession, Player> connectedPlayers = new HashMap<>();

    /**
     * This holds all players in their respective lobbies prior to game start.
     */
    private Map<Integer, Map<WebSocketSession, Player>> lobby = new HashMap<>();

    private Map<Integer, Long> lastSend = new HashMap<>();

    private final int MAX_GAME_SIZE = 5;


    /**
     * This allows the player to navigate and move between servers, this is connected directly to the game socket.
     * @param session the instance of the connection
     * @param message the message being received from the session
     * @throws Exception any exception that may come about due to
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if (payload.equalsIgnoreCase("join")) {
            for (Map.Entry<Integer, Map<WebSocketSession, Player>> entry : this.lobby.entrySet()) {
                if (entry.getValue().size() < MAX_GAME_SIZE) {
                    entry.getValue().put(session, null);
                    session.sendMessage(new TextMessage("lobby number : " + entry.getKey()));
                    return;
                }
            }
            Map<WebSocketSession, Player> newEntry = new HashMap<>();
            newEntry.put(session, null);
            this.lobby.put(this.lobby.size(), newEntry);
            session.sendMessage(new TextMessage("lobby number : " + (this.lobby.size() - 1)));
            return;
        } else if (payload.equalsIgnoreCase("leave")) {
            for (Map.Entry<Integer, Map<WebSocketSession, Player>> entry : this.lobby.entrySet()) {
                if (entry.getValue().containsKey(session)) {
                    entry.getValue().remove(session);

                    session.sendMessage(new TextMessage("Success!"));
                    return;
                }
            }
        } else if (payload.equalsIgnoreCase("refresh")) {
            for (Map.Entry<Integer, Map<WebSocketSession, Player>> entry : this.lobby.entrySet()) {
                if (entry.getValue().containsKey(session)) {
                    List<Player> playersToSend = new ArrayList<>(entry.getValue().values());

                    ByteBuffer bufferToSend = this.tagAndBag(playersToSend, (byte) 4);

                    BinaryMessage binaryMessage = new BinaryMessage(bufferToSend);
                    session.sendMessage(binaryMessage);
                    return;
                }
            }
        } else {
            session.sendMessage(new TextMessage("ERROR: Not a valid argument for utilization, if you think this is a mistake contact John Schirm."));
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        ByteBuffer buffer = message.getPayload();

        Object object = this.returnAndBurn(buffer);
        Player player = null;

        int lobbyNumber = 1000;

        for (Map.Entry<Integer, Map<WebSocketSession, Player>> haha : this.lobby.entrySet()) {
            if (haha.getValue().containsKey(session)) {
                lobbyNumber = haha.getKey();
                break;
            }
        }

        if (lobbyNumber == 1000) {
            for (Map.Entry<Integer, Map<WebSocketSession, Player>> haha : this.lobby.entrySet()) {
                if (haha.getValue().size() < this.MAX_GAME_SIZE) {
                    haha.getValue().put(session, null);
                }
            }
        }

        if (object instanceof Player) {
            player = (Player) object;
            this.connectedPlayers.replace(session, player);
            this.lobby.get(lobbyNumber).replace(session, player);
            sendBinaryData(lobbyNumber, session);
        } else if (object instanceof Location && this.connectedPlayers.get(session) != null) {
            Location location = (Location) object;
            player = this.connectedPlayers.get(session);
            player.setLocation(location);
            this.connectedPlayers.replace(session, player);
            this.lobby.get(lobbyNumber).replace(session, player);
            sendBinaryData(lobbyNumber, session);
        } else {
            session.sendMessage(new TextMessage("For the initial message a Player object is required. session id: " + session.getId() + " lobby number: " + lobbyNumber + "\n" + "object class " + object.getClass().getCanonicalName() + " object string " + object.toString()));
            session.close(CloseStatus.BAD_DATA);
        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        connectedPlayers.put(session, null);
        session.sendMessage(new TextMessage("You have connected!"));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        connectedPlayers.remove(session);
        for (Map.Entry<Integer, Map<WebSocketSession, Player>> haha : this.lobby.entrySet()) {
            if (haha.getValue().containsKey(session)) {
                haha.getValue().remove(session);
                break;
            }
        }
    }


    public synchronized void sendBinaryData(int lobbyNum, WebSocketSession session) throws IOException {
        List<Player> playersToSend = new ArrayList<>(this.lobby.get(lobbyNum).values());

        ByteBuffer bufferToSend = this.tagAndBag(playersToSend, (byte) 4);

        BinaryMessage binaryMessage = new BinaryMessage(bufferToSend);
        if (!this.lastSend.containsKey(lobbyNum)) {
            this.lastSend.put(lobbyNum, 0L);
        }
        if (System.currentTimeMillis() - this.lastSend.get(lobbyNum) > 7) {
            this.lastSend.put(lobbyNum, System.currentTimeMillis()); // Changes the last run.
            for (WebSocketSession webSocketSession : this.lobby.get(lobbyNum).keySet()) {
                if (!webSocketSession.getId().equals(session.getId())) {
                    webSocketSession.sendMessage(binaryMessage);
                }
            }
        }

    }


    /*
    Object to ByteBuffer converter.
     */

    private ByteBuffer tagAndBag(Object object, byte tag) throws IOException {
        byte[] tagArr = { tag };
        byte[] objArr = mapper.writeValueAsBytes(object);
//        byte[] objArr = serialize(object);
        byte[] newArr = new byte[tagArr.length + objArr.length];
        int i = 0;
        for (;i < tagArr.length; i++) {
            newArr[i] = tagArr[i];
        }
        for (byte b : objArr) {
            newArr[i] = b;
            i++;
        }
        return ByteBuffer.wrap(newArr);
    }


    /*
    ByteBuffer to Object converter.
     */

    private Object returnAndBurn(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        int check = buffer.get();
        int currPostion = buffer.position();
        byte[] toGet = Arrays.copyOfRange(buffer.array(), currPostion, buffer.array().length);
        switch (check) {
            case 2:
                try {
                    return mapper.<Player>readValue(toGet, new TypeReference<Player>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    return mapper.<Location>readValue(toGet, new TypeReference<Location>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }

    /**
     * This begins a game that is instanced separately from others.
     * @param toJoin the list of connection to join.
     */
    public void startGame(Map<WebSocketSession, Player> toJoin) {
        //TODO take the toJoin from lobby to activeGame and remove them from lobby.
    }


}
