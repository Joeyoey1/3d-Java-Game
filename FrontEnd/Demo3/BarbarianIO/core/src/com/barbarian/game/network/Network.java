package com.barbarian.game.network;

import com.barbarian.game.attacks.Attack;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network implements Runnable {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static WebSocketSession webSocketSession;

    private Thread t;
    private List<Player> updatedPlayers;
    public boolean fresh_ = false;
    public List<Player> get_updated_players() {return updatedPlayers;}
    public void set_stale() { fresh_ = false;}
    public boolean is_fresh(){ return fresh_; }

    public Network() {
        this.start();
    }

    @Override
    public void run() {
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "playerUpdate");
            t.setName("playerUpdate");
            t.start();
        }
        connect();
    }

    public void connect() {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            TextMessage message = new TextMessage(mapper.writeValueAsString(new Player()));

            webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {

                @Override
                protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
                    try {
                        Object object = returnAndBurn(message.getPayload());
                        if (object instanceof List) {
                            List temp = (List) object;
                            if (temp.get(0) instanceof Player) {
                                updatedPlayers = (List<Player>) temp;
                                fresh_ = true;
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    //System.out.println("received message - " + message.getPayload());
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    System.out.println("established connection - " + session);
                }
                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
                    System.out.println("" + closeStatus.getReason());
                    //connect();
                }

            }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/socket")).get();
            // coms-309-jr-4.misc.iastate.edu
            webSocketSession.sendMessage(new TextMessage("join"));
            } catch (Exception exception) {
//            System.out.println(exception);
        }
    }

    public void sendPlayerUpdate(Player player) {
        if(webSocketSession == null) System.out.println("no connection");
        else if (webSocketSession.isOpen()) {
            try {
                BinaryMessage message = new BinaryMessage(tagAndBag(player, (byte) 2));
//                System.out.println("sent: " + message.getPayload());
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("web socket closed");
            //TODO TERMINATE IF WEBSOCKET DOESNT EXIST.
        }
    }

    public void sendAttackUpdate(Attack attack) {
        if(webSocketSession == null) System.out.println("no connection");
        else if (webSocketSession.isOpen()) {
            try {
                BinaryMessage message = new BinaryMessage(tagAndBag(attack, (byte)5));
//                System.out.println("sent: " + message.getPayload());
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
//            System.out.println("web socket closed");
            //TODO TERMINATE IF WEBSOCKET DOESNT EXIST.
        }
    }


    public void exit() {
        try {
            webSocketSession.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

        /*
    Object to ByteBuffer converter.
     */

    private ByteBuffer tagAndBag(Object object, byte tag) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
        objectOutputStream.writeByte(tag);
        if (object instanceof Player) {
            SerializerUtils.writePlayer((Player) object, objectOutputStream);
        } else if (object instanceof Location) {
            SerializerUtils.writeLocation((Location) object, objectOutputStream);
        }

        objectOutputStream.flush();
        baos.flush();
        byte[] out = baos.toByteArray();
        objectOutputStream.close();
        baos.close();
        //        byte[] tagArr = { tag };
//        byte[] objArr = mapper.writeValueAsBytes(object);
////        byte[] objArr = serialize(object);
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

        System.out.println(check + " Check Byte");

        //        int check = buffer.get();
//        int currPostion = buffer.position();
//        byte[] toGet = Arrays.copyOfRange(buffer.array(), currPostion, buffer.array().length);
        Object out = null;
        switch (check) {
            case 2:
                try {
                    out = SerializerUtils.readPlayer(objectInputStream);
//                    return mapper.<Player>readValue(toGet, new TypeReference<Player>() {
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    out = SerializerUtils.readLocation(objectInputStream);
//                    return mapper.<Location>readValue(toGet, new TypeReference<Location>() {
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    List<Player> players = new ArrayList<>();
                    int numPlayers = objectInputStream.readInt();
                    System.out.println(numPlayers + " Num players.");
                    for (int i = 0; i < numPlayers; i++) {
                        players.add(SerializerUtils.readPlayer(objectInputStream));
                    }
                    out = players;
//                    return mapper.readValue(toGet, new TypeReference<List<Player>>() {
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        objectInputStream.close();
        baos.close();
        return out;
    }
}
