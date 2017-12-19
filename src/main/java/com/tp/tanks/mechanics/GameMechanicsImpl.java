package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.*;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.WorldEngine;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.mechanics.requests.SpawnRequest;
import com.tp.tanks.mechanics.world.ScoresToSend;
import com.tp.tanks.mechanics.world.World;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
public class GameMechanicsImpl implements GameMechanics {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    @NotNull
    private final TankSnapshotService tankSnapshotsService;

    @NotNull
    private final ServerSnapshotService serverSnapshotService;

    @NotNull
    private final WorldEngine engine;

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @NotNull
    private RemotePointService remotePointService;

    private long lastStatisticSendTime;

    @NotNull
    private World world;

    public GameMechanicsImpl(@NotNull TankSnapshotService tankSnapshotsService,
                             @NotNull ServerSnapshotService serverSnapshotService,
                             @NotNull RemotePointService remotePointService) {
        this.tankSnapshotsService = tankSnapshotsService;
        this.serverSnapshotService = serverSnapshotService;
        this.remotePointService = remotePointService;
        this.world = new World();
        this.engine = new WorldEngine(this.world, remotePointService);
        this.lastStatisticSendTime = System.currentTimeMillis();
    }


    @Override
    public void addTankSnapshot(@NotNull Long userId, @NotNull TankSnap clientSnap) {
        tasks.add(() -> tankSnapshotsService.pushTankSnap(userId, clientSnap));
    }

    @Override
    public void addUser(@NotNull Long userId) {
        LOGGER.info("add new user: userId = " + userId.toString());
    }

    @Override
    public void getNewSpawnPoint(@NotNull Long userId, SpawnRequest request) {

        SpawnSnap spawnSnap = new SpawnSnap();
        Coordinate coordinate = this.world.getTanksPosition();
        spawnSnap.setPosition(coordinate);

        TankSnap tankSnap = new TankSnap();
        tankSnap.setTurretAngle(0);
        tankSnap.setPlatformAngle(0);
        tankSnap.setShoot(false);
        tankSnap.setUserId(userId);
        tankSnap.setUsername(request.getUsername());
        tankSnap.setHealth(100);
        tankSnap.setPlatform(coordinate);

        remotePointService.spawnUser(userId);
        addTankSnapshot(userId, tankSnap);

        try {
            this.remotePointService.sendMessageToUser(userId, spawnSnap);
        } catch (IOException ex) {
            LOGGER.error("[GameMechanicsImpl: getNewSpawnPoint] IOException: ", ex);
        }
    }

    @Override
    public void gmStep(long frameTime) {
        while (!tasks.isEmpty()) {
            final Runnable nextTask = tasks.poll();
            if (nextTask != null) {
                try {
                    nextTask.run();
                } catch (RuntimeException ex) {
                    LOGGER.error("Can't process game task", ex);
                }
            }
        }

        List<TankSnap> tankSnapshots = tankSnapshotsService.processSnapshots();
        List<Line> shootingLines = tankSnapshotsService.shootingLines();

        engine.process(tankSnapshots, shootingLines);

        serverSnapshotService.send(tankSnapshots, remotePointService.getTanksStats());

        long time = System.currentTimeMillis();
        if (time - lastStatisticSendTime > 2000) {

            lastStatisticSendTime = time;
            List<ScoresToSend> scoresToSend = remotePointService.getTopPlayers(5);
            serverSnapshotService.sendStatistics(scoresToSend);
        }

        tankSnapshotsService.reset();
    }
}
