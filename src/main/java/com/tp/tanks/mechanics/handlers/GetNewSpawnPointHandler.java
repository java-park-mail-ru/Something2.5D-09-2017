package com.tp.tanks.mechanics.handlers;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.requests.GetNewSpawnPoint;
import com.tp.tanks.websocket.MessageHandler;
import com.tp.tanks.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GetNewSpawnPointHandler extends MessageHandler<GetNewSpawnPoint.Request> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public GetNewSpawnPointHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(GetNewSpawnPoint.Request.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(GetNewSpawnPoint.Request.class, this);
    }

    @Override
    public void handle(@NotNull GetNewSpawnPoint.Request message, @NotNull Long userId) {
        gameMechanics.getNewSpawnPoint(userId);
    }
}