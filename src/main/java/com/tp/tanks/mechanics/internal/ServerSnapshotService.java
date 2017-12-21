package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.StatisticsSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.mechanics.world.ScoresToSend;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ServerSnapshotService {

    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSnapshotService.class);

    public ServerSnapshotService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void send(List<TankSnap> tanks, Map<Long, Scores> tanksStats) {

        if (tanks.isEmpty()) {
            return;
        }

        final ServerSnap serverSnap = new ServerSnap();

        for (TankSnap snap : tanks) {

            try {
                final Integer kills = tanksStats.get(snap.getUserId()).getKills();
                LOGGER.info("userID = " + snap.getUserId().toString() + "; kills = " + kills.toString());
                snap.setKills(kills);
            } catch (NullPointerException err) {
                LOGGER.error(err.toString());
                snap.setKills(0);
            }
        }

        serverSnap.setTanks(tanks);
        serverSnap.setPlayers(remotePointService.getPlayers());

        for (TankSnap tankSnap: tanks) {
            try {
                  remotePointService.sendMessageToUser(tankSnap.getUserId(), serverSnap);
            } catch (IOException e) {
                LOGGER.error("Can't send server snapshot to client: userId = " + tankSnap.getUserId().toString());
            }
        }
    }

    public void sendStatistics(List<ScoresToSend> scoresToSend) {
        Set<Long> availablePlayers = remotePointService.getPlayers();

        StatisticsSnap snap = new StatisticsSnap();
        snap.setLeaders(scoresToSend);

        for (Long userId: availablePlayers) {
            try {
                remotePointService.sendMessageToUser(userId, snap);
            } catch (IOException ex) {
                LOGGER.error("Can't send server statistics to client: userId = " + userId.toString());
            }
        }
    }
}
