package com.tp.tanks.mechanics.base;

import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;


public class MapSnap extends Message {

    @NotNull Coordinate box;

    public Coordinate getBox() {
        return box;
    }

    public void setBox(Coordinate box) {
        this.box = box;
    }
}
