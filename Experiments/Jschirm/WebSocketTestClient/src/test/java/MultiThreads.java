import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class MultiThreads implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ScheduledExecutorService schedule = newSingleThreadScheduledExecutor();


    private Thread t;
    private String threadName;
    private int attemps;
    private Long currentPing;
    private int frequencyMillis;
    private boolean enabled;

    MultiThreads(String name, int frequencyMillis, boolean enabled) {
        threadName = name;
        System.out.println("Creating " +  threadName );
        attemps = 0;
        this.enabled = enabled;
        this.frequencyMillis = frequencyMillis;
        this.start();
    }

    public void run() {
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.setName(threadName);
            t.start ();
        }
        connect();
    }


    private void connect() {
        try {
            WebSocketClient webSocketClient = new StandardWebSocketClient();
            ChatMessage player = new ChatMessage(0,"Hello, this is a test!");

            WebSocketSession webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
                @Override
                protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
                    try {
                        Object object = returnAndBurn(message.getPayload());

                        if (object instanceof List) {
                            List test = (List) object;
                            if (!test.isEmpty() &&test.get(0) instanceof Player) {
                                List<Player> playerList = test;
                                if (enabled) {
                                    for (Player player1 : playerList) {
                                        if (player1 != null) {
                                            LOGGER.info(session.getId() + " : " + player1.getId());
                                        }
                                    }
                                }
                            }
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
                    LOGGER.info(message.getPayload());
                }

                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    LOGGER.info("established connection - " + session);
                }

            }, new WebSocketHttpHeaders(), URI.create("ws://coms-309-jr-4.misc.iastate.edu:8080/lobby")).get();

            TextMessage message = new TextMessage("join");
            webSocketSession.sendMessage(message);

            schedule.scheduleAtFixedRate(() -> {

                try {
                    BinaryMessage binaryMessage = new BinaryMessage(tagAndBag(player, (byte) 2));

                    //LOGGER.info("Session ID: " + webSocketSession.getId() + ": message-payload-attempt - " + message.getPayload());
                    //LOGGER.info("Session ID: " + webSocketSession.getId() + " -- Sending");
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(binaryMessage);
                        currentPing = System.currentTimeMillis();
                        attemps = 0;
                        //LOGGER.info("Session ID: " + webSocketSession.getId() + ": sent message - " + message.getPayload());
                    } else {
                        attemps++;
                        //if (attemps > 5) {
                            schedule.shutdown(); // Shutsdown the scheduler if the connection is closed.
                            LOGGER.error("Session ID: " + webSocketSession.getId() + ": has been closed; cause Connection ERROR");
                        //} else {
                            //connect(); // Attempts a new Connection
                            //LOGGER.error("Session ID: " + webSocketSession.getId() + ": lost connection, attempting to reconnect attempt: " + attemps);
                            //schedule.shutdown(); // Shutsdown the current scheduler
                        //}
                    }
                } catch (Exception e) {
                    LOGGER.error("Exception while sending a message", e);
                    try {
                        webSocketSession.close(CloseStatus.SERVER_ERROR);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (webSocketSession.isOpen()) {
                    try {
                        webSocketSession.sendMessage(new TextMessage("refresh"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 1, this.frequencyMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.error("Exception while accessing websockets", e);
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
                    return mapper.readValue(toGet, new TypeReference<Player>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    return mapper.readValue(toGet, new TypeReference<Location>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    return mapper.readValue(toGet, new TypeReference<List<Player>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }


}
