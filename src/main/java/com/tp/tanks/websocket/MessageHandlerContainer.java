package com.tp.tanks.websocket;

import org.jetbrains.annotations.NotNull;


public interface MessageHandlerContainer {

    void handle(@NotNull Message message, @NotNull Long userID) throws HandleException;

    <T extends Message> void registerHandler(@NotNull Class<T> clazz, MessageHandler<T> handler);
}
