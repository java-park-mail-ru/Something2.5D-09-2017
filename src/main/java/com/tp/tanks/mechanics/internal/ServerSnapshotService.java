package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ServerSnapshotService {

    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSnapshotService.class);

    public ServerSnapshotService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void send(List<TankSnap> tanks) {

        if (tanks.isEmpty()) {
            return;
        }

        final ServerSnap serverSnap = new ServerSnap();
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
}
