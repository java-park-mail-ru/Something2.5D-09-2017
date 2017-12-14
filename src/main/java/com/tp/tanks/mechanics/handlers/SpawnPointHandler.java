package com.tp.tanks.mechanics.handlers;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.requests.SpawnRequest;
import com.tp.tanks.websocket.MessageHandler;
import com.tp.tanks.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpawnPointHandler extends MessageHandler<SpawnRequest> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public SpawnPointHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(SpawnRequest.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(SpawnRequest.class, this);
    }

    @Override
    public void handle(@NotNull SpawnRequest message, @NotNull Long userId) {
        gameMechanics.getNewSpawnPoint(userId, message);
    }
}