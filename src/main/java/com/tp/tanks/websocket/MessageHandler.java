package com.tp.tanks.websocket;

import org.jetbrains.annotations.NotNull;


public abstract class MessageHandler<T extends Message> {

    @NotNull
    private final Class<T> clazz;

    public MessageHandler(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("OverlyBroadCatchBlock")
    public void handleMessage(@NotNull Message message, @NotNull Long userId) throws HandleException {
        try {
            handle(clazz.cast(message), userId);
        } catch (ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), ex);
        }
    }

    public abstract void handle(@NotNull T message, @NotNull Long userId) throws HandleException;
}
