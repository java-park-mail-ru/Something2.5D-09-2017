package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.MapSnap;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MapSnapService {

    @NotNull
    private final RemotePointService remotePointService;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSnapshotService.class);

    public MapSnapService(@NotNull RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
    }

    public void send(Long userId) {
        MapSnap mapSnap = new MapSnap();
        mapSnap.setBox(new Coordinate(10.2, 12.3));

        try {
            LOGGER.info("[MapSnapService: send]: trying to send coordinate to user = " + userId);
            remotePointService.sendMessageToUser(userId, mapSnap);
            LOGGER.info("[MapSnapService: send]: sended coordinate to user = " + userId);
        } catch (IOException e) {
            LOGGER.error("[MapSnapService: send]Can't send server MapSnap to client: userId = " + userId);
        }
    }
}
