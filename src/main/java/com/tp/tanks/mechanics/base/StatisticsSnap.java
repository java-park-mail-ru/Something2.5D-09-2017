package com.tp.tanks.mechanics.base;

import com.tp.tanks.mechanics.world.ScoresToSend;
import com.tp.tanks.websocket.Message;

import java.util.List;

public class StatisticsSnap extends Message {

    private List<ScoresToSend> leaders;

    public List<ScoresToSend> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<ScoresToSend> leaders) {
        this.leaders = leaders;
    }
}
