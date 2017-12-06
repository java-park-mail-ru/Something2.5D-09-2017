package com.tp.tanks.mechanics.handlers;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.requests.JoinGame;
import com.tp.tanks.websocket.MessageHandler;
import com.tp.tanks.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;

/**
 * Created by Solovyev on 03/11/2016.
 */
@Component
public class JoinGameHandler extends MessageHandler<JoinGame.Request> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public JoinGameHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(JoinGame.Request.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(JoinGame.Request.class, this);
    }

    @Override
    public void handle(@NotNull JoinGame.Request message, @NotNull Long userId) {
        gameMechanics.addUser(userId);
    }
}
