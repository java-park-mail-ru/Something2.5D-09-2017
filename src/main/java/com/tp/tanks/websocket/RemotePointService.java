package com.tp.tanks.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class RemotePointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemotePointService.class);
    private Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Set<Long> players = new ConcurrentSkipListSet<>();
    private final ObjectMapper objectMapper;

    public RemotePointService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerUser(@NotNull Long userId, @NotNull WebSocketSession webSocketSession) {
        LOGGER.info("[RemotePointService.registerUser] register userID = " + userId.toString());
        sessions.put(userId, webSocketSession);
        players.add(userId);
    }

    public boolean isConnected(@NotNull Long userId) {
        return sessions.containsKey(userId) && sessions.get(userId).isOpen() && players.contains(userId);
    }

    public Set<Long> getPlayers() {
        return players;
    }

    public void removeUser(@NotNull Long userId) {
        sessions.remove(userId);
        players.remove(userId);
        LOGGER.info("[RemotePointService.removeUser] unregister userID = " + userId.toString());
    }

    public void cutDownConnection(@NotNull Long userId, @NotNull CloseStatus closeStatus) {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close(closeStatus);
            } catch (IOException ignore) {
                LOGGER.error("Can't close session");
            }
        }
    }

    public void sendMessageToUser(@NotNull Long userId, @NotNull Message message) throws IOException {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession == null) {
            LOGGER.error("[RemotePointService.sendMessageToUser] webSocketSession is null");
            throw new IOException("no game websocket for user " + userId);
        }
        if (!webSocketSession.isOpen()) {
            LOGGER.error("[RemotePointService.sendMessageToUser] session is closed or not exsists");
            throw new IOException("session is closed or not exsists");
        }
        //noinspection OverlyBroadCatchBlock
        try {
            //noinspection ConstantConditions

            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            throw new IOException("Unnable to send message", e);
        }
    }
}
