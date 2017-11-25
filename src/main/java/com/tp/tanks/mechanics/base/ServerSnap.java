package com.tp.tanks.mechanics.base;

import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"NullableProblems"})
public class ServerSnap extends Message {

    @NotNull private List<TankSnap> tanks;

    @NotNull
    public List<TankSnap> getTanks() {
        return tanks;
    }

    public void setTanks(@NotNull List<TankSnap> tanks) {
        this.tanks = tanks;
    }
}
