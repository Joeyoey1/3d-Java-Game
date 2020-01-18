package com.jschirm.playerservice.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jschirm.playerservice.model.HitPacket;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
import com.jschirm.playerservice.utils.SerializerUtils;
import org.springframework.util.SerializationUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Logger;

public class GameSocketHandler extends AbstractWebSocketHandler {


    /*
    These are the data byte heads

    2 -> Player
    3 -> Location
    4 -> List<Player>
    5 -> HitPacket
    6 -> List<HitPacket>
     */




    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * All players connected, this includes players in lobbies and separate coliseums
     */
    private Map<WebSocketSession, Player> connectedPlayers = new HashMap<>();

    /**
     * This holds all players in their respective lobbies prior to game start.
     */
    private Map<Integer, Map<WebSocketSession, Player>> lobby = new HashMap<>();

    private List<HitPacket> queue = new ArrayList<>();

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
                    session.sendMessage(new TextMessage("game number : " + entry.getKey()));
                    return;
                }
            }
            Map<WebSocketSession, Player> newEntry = new HashMap<>();
            newEntry.put(session, null);
            this.lobby.put(this.lobby.size(), newEntry);
            session.sendMessage(new TextMessage("game number : " + (this.lobby.size() - 1)));
            return;
        } else if (payload.equalsIgnoreCase("leave")) {
            for (Map.Entry<Integer, Map<WebSocketSession, Player>> entry : this.lobby.entrySet()) {
                if (entry.getValue().containsKey(session)) {
                    entry.getValue().remove(session);
                    // this sends the success message!
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




//        Player playerUpdate = mapper.readValue(message.getPayload(), Player.class);
//        if (playerUpdate == null) {
//            connectedPlayers.remove(session);
//
//            session.close(CloseStatus.BAD_DATA);
//            return;
//        }
//
//        connectedPlayers.put(session, playerUpdate);
//
//
//
//        Set<WebSocketSession> nullPlayer = new HashSet<>();
//        for (Map.Entry<WebSocketSession, Player> entry : connectedPlayers.entrySet()) {
//            if (entry.getValue() == null) {
//                nullPlayer.add(entry.getKey());
//            }
//        }
//
//        List<Player> playersToSend = new ArrayList<>(connectedPlayers.values());
//
//        TextMessage textMessage = new TextMessage(mapper.writeValueAsString(playersToSend));
//
//
//        if (System.currentTimeMillis() - lastSend > 20) {
//            lastSend = System.currentTimeMillis(); // Changes the last run.
//            for (WebSocketSession webSocketSession : connectedPlayers.keySet()) {
//                webSocketSession.sendMessage(textMessage);
//            }
//        }

        //session.sendMessage(new TextMessage("Accepted thanks for sending"));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        ByteBuffer buffer = message.getPayload();

        Object object = this.returnAndBurn(buffer);
        Player player = null;

        int lobbyNumber = -1;

        for (Map.Entry<Integer, Map<WebSocketSession, Player>> haha : this.lobby.entrySet()) {
            if (haha.getValue().containsKey(session)) {
                lobbyNumber = haha.getKey();
                break;
            }
        }

        if (lobbyNumber == -1) {
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
            System.out.println("Received Player. Player ID: " + player.getId() + " Lobby number: " + lobbyNumber);
        } else if (object instanceof Location && this.connectedPlayers.get(session) != null) {
            Location location = (Location) object;
            player = this.connectedPlayers.get(session);
            player.setLocation(location);
            this.connectedPlayers.replace(session, player);
            this.lobby.get(lobbyNumber).replace(session, player);
            sendBinaryData(lobbyNumber, session);
        } else if (object instanceof HitPacket) {
            HitPacket hitPacket = (HitPacket) object;
            this.dealWithAttack(hitPacket, lobbyNumber);
        } else {
            session.sendMessage(new TextMessage("For the initial message a Player object is required."));
            //session.close(CloseStatus.BAD_DATA);
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

    private synchronized void sendBinaryData(int lobbyNum, WebSocketSession session) throws IOException {
        //System.out.println("Sending list.");
        List<Player> playersToSend = new ArrayList<>(this.lobby.get(lobbyNum).values());

        //System.out.println(playersToSend.size() + " players size.");


        ByteBuffer bufferToSend = this.tagAndBag(playersToSend, (byte) 4);

        BinaryMessage binaryMessage = new BinaryMessage(bufferToSend);
        if (!this.lastSend.containsKey(lobbyNum)) {
            this.lastSend.put(lobbyNum, 0L);
        }
        if (System.currentTimeMillis() - this.lastSend.get(lobbyNum) > 7) {
            this.lastSend.put(lobbyNum, System.currentTimeMillis()); // Changes the last run.
            for (WebSocketSession webSocketSession : this.lobby.get(lobbyNum).keySet()) {
                webSocketSession.sendMessage(binaryMessage);
            }
            if (!queue.isEmpty()) {
                ByteBuffer toSend = this.tagAndBag(this.queue, (byte) 6);
                BinaryMessage binMessage = new BinaryMessage(toSend);
                for (WebSocketSession webSocketSession : this.lobby.get(lobbyNum).keySet()) {
                    if (!webSocketSession.getId().equals(session.getId())) {
                        webSocketSession.sendMessage(binMessage);
                    }
                }
                this.queue.clear();
            }
        }

    }


    /*
    Object to ByteBuffer converter.
     */

    private ByteBuffer tagAndBag(Object object, byte tag) throws IOException {
//        byte[] tagArr = { tag };
//        byte[] objArr = mapper.writeValueAsBytes(object);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeByte(tag); // Writes the check byte
        if (object instanceof Player) {
            SerializerUtils.writePlayer((Player) object, objectOutputStream);
        } else if (object instanceof Location) {
            SerializerUtils.writeLocation((Location) object, objectOutputStream);
        } else if (object instanceof List) {
            List temp = (List) object;
            if (temp.get(0) instanceof Player) {
                List<Player> players = temp;
                objectOutputStream.writeInt(players.size());
                for (Player player : players) {
                    SerializerUtils.writePlayer(player, objectOutputStream);
                }
            }
        }

        objectOutputStream.flush();
        baos.flush();
        byte[] out = baos.toByteArray();
        System.out.println(out.length + " bytes being sent");
        objectOutputStream.close();
        baos.close();
//        byte[] objArr = serialize(object);
//        byte[] newArr = new byte[tagArr.length + objArr.length];
//        int i = 0;
//        for (;i < tagArr.length; i++) {
//            newArr[i] = tagArr[i];
//        }
//        for (byte b : objArr) {
//            newArr[i] = b;
//            i++;
//        }
        return ByteBuffer.wrap(out);
    }


    /*
    ByteBuffer to Object converter.
     */

    private Object returnAndBurn(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream baos = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(baos);
        int check = objectInputStream.readByte();

//        int check = buffer.get();
//        int currPostion = buffer.position();
//        byte[] toGet = Arrays.copyOfRange(buffer.array(), currPostion, buffer.array().length);
        Object out = null;
        switch (check) {
            case 2:
                try {
                    out = SerializerUtils.readPlayer(objectInputStream);
//                    return mapper.readValue(toGet, new TypeReference<Player>() {
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    out = SerializerUtils.readLocation(objectInputStream);
//                    return mapper.readValue(toGet, new TypeReference<Location>() {
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
//            case 5:
//                try {
//                    out = (HitPacket) objectInputStream.readObject();
////                    return mapper.readValue(toGet, new TypeReference<HitPacket>(){
////                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        }
        objectInputStream.close();
        baos.close();
        return out;
    }

    /**
     * This begins a game that is instanced separately from others.
     * @param toJoin the list of connection to join.
     */
    public void startGame(Map<WebSocketSession, Player> toJoin) {
        //TODO take the toJoin from lobby to activeGame and remove them from lobby.
    }

    public void dealWithAttack(HitPacket hitPacket, int lobbyNumber) {
        Player attacker = null;
        Player victim = null;
        Location atk = null;
        Location vic = null;
        for (Map.Entry<WebSocketSession, Player> entry : this.lobby.get(lobbyNumber).entrySet()) {
            if (entry.getValue().getId() == hitPacket.getAttacker_()) {
                attacker = entry.getValue();
                atk = attacker.getLocation();
            }
            if (entry.getValue().getId() == hitPacket.getVictim_()) {
                victim = entry.getValue();
                vic = victim.getLocation();
            }
        }
        if (vic != null && atk != null) {
            Location center = hitPacket.getCenterPoint();
            double attkToCenter = WebSocketConfig.getDistance(atk, center);
            double centerToVic = WebSocketConfig.getDistance(center, vic);
            if (attkToCenter < 5 && centerToVic < 5) {
                switch (hitPacket.getAttackID()) {
                    case 0:
                        victim.setHealth(victim.getHealth() - 9);
                        break;
                    case 1:
                        victim.setHealth(victim.getHealth() - 9);
                        System.out.println(victim.getId() + " was attacked by " + attacker.getId() + " for 9 damage");
                        break;
                }
            }
        } else {
            return;
        }
        for (Map.Entry<WebSocketSession, Player> entry : this.lobby.get(lobbyNumber).entrySet()) {
            if (entry.getValue().getId() == victim.getId()) {
                entry.setValue(victim);
                break;
            }
        }
    }




}
