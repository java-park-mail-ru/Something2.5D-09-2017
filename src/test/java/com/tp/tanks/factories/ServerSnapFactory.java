package com.tp.tanks.factories;

import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.TankSnap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerSnapFactory {

    public static ServerSnap create() {
        int amount = 10;
        List<TankSnap> tanks = new ArrayList<>();
        Set<Long> players = createPlayers(amount);

        for(Long userId : players) {
            tanks.add(TankSnapFactory.createOneForUser(userId));
        }

        ServerSnap serverSnap = new ServerSnap();
        serverSnap.setTanks(tanks);
        serverSnap.setPlayers(players);

        return serverSnap;
    }

    private static Set<Long> createPlayers(int amount) {
        Set<Long> players = new HashSet<>();
        for(int i = 0; i < amount; ++i) {
            players.add(Generators.generateLong());
        }
        return players;
    }
}
