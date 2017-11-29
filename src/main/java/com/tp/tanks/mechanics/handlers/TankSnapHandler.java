package com.tp.tanks.mechanics.handlers;

import com.tp.tanks.mechanics.GameMechanics;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.websocket.MessageHandler;
import com.tp.tanks.websocket.MessageHandlerContainer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;

/**
 * Created by Solovyev on 03/11/2016.
 */
@Component
public class TankSnapHandler extends MessageHandler<TankSnap> {
    @NotNull
    private GameMechanics gameMechanics;
    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public TankSnapHandler(@NotNull GameMechanics gameMechanics, @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(TankSnap.class);
        this.gameMechanics = gameMechanics;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(TankSnap.class, this);
    }

    @Override
    public void handle(@NotNull TankSnap message, @NotNull Long userId) {
        gameMechanics.addTankSnapshot(userId, message);
    }
}
