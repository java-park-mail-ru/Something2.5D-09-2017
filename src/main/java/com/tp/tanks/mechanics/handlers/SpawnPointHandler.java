package com.tp.tanks.mechanics.handlers;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.requests.SpawnPoint;
import com.tp.tanks.websocket.MessageHandler;
import com.tp.tanks.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpawnPointHandler extends MessageHandler<SpawnPoint.Request> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public SpawnPointHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(SpawnPoint.Request.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(SpawnPoint.Request.class, this);
    }

    @Override
    public void handle(@NotNull SpawnPoint.Request message, @NotNull Long userId) {
        gameMechanics.getNewSpawnPoint(userId);
    }
}