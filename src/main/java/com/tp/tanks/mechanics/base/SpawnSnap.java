package com.tp.tanks.mechanics.base;

import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;

public class SpawnSnap extends Message {

    @NotNull
    private Coordinate position;

    public SpawnSnap() {
    }


    public SpawnSnap(@NotNull Coordinate position) {
        this.position = position;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
