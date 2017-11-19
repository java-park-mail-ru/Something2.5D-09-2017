package com.tp.tanks.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.tp.tanks.services.UserService;


import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;


public class GameWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");

    private @NotNull UserService userService;
    private final @NotNull MessageHandlerContainer messageHandlerContainer;
    private final @NotNull RemotePointService remotePointService;

    private final ObjectMapper objectMapper;


    public GameWebSocketHandler(@NotNull MessageHandlerContainer messageHandlerContainer,
                                @NotNull UserService userService,
                                @NotNull RemotePointService remotePointService,
                                ObjectMapper objectMapper) {
        this.messageHandlerContainer = messageHandlerContainer;
        this.userService = userService;
        this.remotePointService = remotePointService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        final Long userId = (Long) webSocketSession.getAttributes().get("userId");
        if (userId == null || userService.getById(userId) == null) {
            LOGGER.warn("[GameWebSocketHandler.afterConnectionEstablished] Can't get user by id = " + userId);
            closeSessionSilently(webSocketSession, ACCESS_DENIED);
            return;
        }
        remotePointService.registerUser(userId, webSocketSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        if (!webSocketSession.isOpen()) {
            return;
        }
        final Long userId = (Long) webSocketSession.getAttributes().get("userId");
        if (userId == null) {
            LOGGER.warn("[GameWebSocketHandler.handleTextMessage] Can't get userId from webSocketSession");
            closeSessionSilently(webSocketSession, ACCESS_DENIED);
            return;
        }

        handleMessage(userId, message);
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    private void handleMessage(Long userId, TextMessage text) {
        final Message message;
        try {
            message = objectMapper.readValue(text.getPayload(), Message.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at game response", ex);
            return;
        }
        try {
            //noinspection ConstantConditions
            messageHandlerContainer.handle(message, userId);
        } catch (HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getClass().getName() + " with content: " + text, e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        final Long userId = (Long) webSocketSession.getAttributes().get("userId");
        if (userId == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + closeStatus + ')');
            return;
        }
        remotePointService.removeUser(userId);
    }

    private void closeSessionSilently(@NotNull WebSocketSession session, @Nullable CloseStatus closeStatus) {
        final CloseStatus status = closeStatus == null ? SERVER_ERROR : closeStatus;
        //noinspection OverlyBroadCatchBlock
        try {
            session.close(status);
        } catch (Exception ignore) {
        }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
