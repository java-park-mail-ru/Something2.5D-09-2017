package com.tp.tanks.mechanics.requests;


import com.tp.tanks.websocket.Message;

public class FinishGame extends Message {
    private Overcome overcome;

    public FinishGame(Overcome overcome) {
        this.overcome = overcome;
    }

    public Overcome getOvercome() {
        return overcome;
    }

    @SuppressWarnings("FieldNamingConvention")
    public enum Overcome {
        WIN,
        LOSE,
        DRAW
    }
}
