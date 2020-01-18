package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;
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
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class NetworkSystem extends IteratingSystem implements Runnable {
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<PlayerComponent> player_m_ = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    private static WebSocketSession webSocketSession;

    private Thread t;
    private List<Player> updatedPlayers;
    private Ticker ticker = new Ticker();


    /**
     * creates new network system
     */
    public NetworkSystem () {
        super(Family.all(TransformComponent.class, PlayerComponent.class).get());
        this.start();
        ticker.start();
    }

    /**
     * @param entity
     * @param deltaTime
     * send player position, rotation, etc. information to server
     */
    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PlayerComponent player_c = player_m_.get(entity);
        TransformComponent transform_c = transform_m_.get(entity);
        MovementComponent movement_c = movement_m_.get(entity);

        if(player_c.getID() == Game.thisID) {
            Location player_l = player_c.getLocation();
            Vector3 transform_p = transform_c.get_position();

            player_l.setX(transform_p.x);
            player_l.setY(transform_p.y);
            player_l.setZ(transform_p.z);
            player_l.setW(transform_c.rotation.z);

            Vector3 player_v = movement_c.get_velocity();
            player_l.setVelX(player_v.x);
            player_l.setVelZ(player_v.z);

            if (ticker.get_ticks(deltaTime) > 1) {
                sendPlayerUpdate(player_c.getEntityPlayer());
                ticker.start();
            }
        }
    }

    @Override
    public void run() {
    }

    /**
     * starts new thread to send info
     */
    public void start() {
        if (t == null) {
            t = new Thread(this, "playerUpdate");
            t.setName("playerUpdate");
            t.start();
        }
        connect();
    }

    /**
     * establishes connection to server
     */
    public void connect() {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            TextMessage message = new TextMessage(mapper.writeValueAsString(new Player()));
            webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                /**
                 * @param session
                 * @param message
                 * gets and parses info from server
                 */
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    //LOGGER.info("received message - " + message.getPayload());
                    System.out.println("received message - " + message.getPayload());
                    try {
                        System.out.println("entered try");
                        updatedPlayers = mapper.readValue(message.getPayload(), new TypeReference<List<Player>>() {});
                        System.out.println("num players: " + updatedPlayers.size());
                        for (Player player : updatedPlayers) {
                            if(player == null){}
                            else if(Game.players.get(player.getId()) == null){
                                Random rand = new Random(player.getId());
                                Entity player_n = new Entity();
                                float x = rand.nextFloat() % 20;
                                float z = rand.nextFloat() % 20;
                                player_n.add(new TransformComponent(new Vector3(x, 0f, z), Vector3.Zero, 5f));
                                player_n.add(new InstanceComponent(Game.player_mo));
                                player_n.add(new CollisionComponent(new Vector3(2f, 10f, 1f)));
                                player_n.add(new MovementComponent(10f));
                                player_n.add(new DirectionComponent());
                                Game.players.put(player.getId(), player_n);
                                getEngine().addEntity(Game.players.get(player.getId()));
                            }
                            else if(player.getId() != Game.thisID){
                                TransformComponent transform = transform_m_.get(Game.players.get(player.getId()));
                                MovementComponent movement_c = movement_m_.get(Game.players.get(player.getId()));
                                DirectionComponent direction_c = direction_m_.get(Game.players.get(player.getId()));

                                Location location = player.getLocation();
                                transform.set_position(new Vector3(location.getX(), location.getY(), location.getZ()));
                                direction_c.set_rotation_z(location.getW());
                                movement_c.set_velocity(new Vector3(location.getVelX(), 0f, location.getVelZ()));
                            }
                        }
                    }
                    catch (IOException e) {
                        System.out.println("no other players");
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

            }, new WebSocketHttpHeaders(), URI.create("ws://coms-309-jr-4.misc.iastate.edu:8080/socket")).get();
        } catch (Exception exception) {
        }
    }

    /**
     * @param player
     * send player data to server
     */
//
    public void sendPlayerUpdate(Player player) {
        if(webSocketSession == null) System.out.println("no connection");
        else if (webSocketSession.isOpen()) {
            try {
                TextMessage message = new TextMessage(mapper.writeValueAsString(player));
                System.out.println("sent: " + message.getPayload());
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //TODO TERMINATE IF WEBSOCKET DOESNT EXIST.
        }
    }
}
