package com.tp.tanks.mechanics.base;

import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"NullableProblems"})
public class ServerSnap extends Message {

    @NotNull
    private List<TankSnap> tanks;

    @NotNull
    private Set<Long> players;

    @NotNull
    private List<StatisticsSnap> statistics = new ArrayList<>();

    @NotNull
    public List<TankSnap> getTanks() {
        return tanks;
    }

    public void setTanks(@NotNull List<TankSnap> tanks) {
        this.tanks = tanks;
    }

    public Set<Long> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Long> players) {
        this.players = players;
    }

    public void addStatistics(StatisticsSnap snap) {
        statistics.add(snap);
    }
}
