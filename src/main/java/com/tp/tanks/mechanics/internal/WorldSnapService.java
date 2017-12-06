package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.world.World;
import com.tp.tanks.mechanics.base.WorldSnap;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WorldSnapService {

    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private final World world;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldSnapService.class);

    public WorldSnapService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
        this.world = new World();
    }

    public void send(Long userId) {
        WorldSnap snap = new WorldSnap();
        snap.setBoxes(world.getBoxes());
        snap.setStartTankPosition(world.getTanksPosition());
        snap.setSpawnPoints(world.getSpawnPoints());

        try {
            LOGGER.info("[WorldSnapService: send]: trying to send coordinate to user = " + userId);
            remotePointService.sendMessageToUser(userId, snap);
            LOGGER.info("[WorldSnapService: send]: sended coordinate to user = " + userId);
        } catch (IOException e) {
            LOGGER.error("[WorldSnapService: send]Can't send server WorldSnap to client: userId = " + userId);
        }
    }
}
