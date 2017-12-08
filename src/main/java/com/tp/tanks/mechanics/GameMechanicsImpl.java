package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.SpawnSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.ShootingService;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.mechanics.requests.SpawnRequest;
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
    private final ShootingService shootingService;

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @NotNull
    private RemotePointService remotePointService;

    @NotNull
    private World world;

    public GameMechanicsImpl(@NotNull TankSnapshotService tankSnapshotsService,
                             @NotNull ServerSnapshotService serverSnapshotService,
                             @NotNull RemotePointService remotePointService) {
        this.tankSnapshotsService = tankSnapshotsService;
        this.serverSnapshotService = serverSnapshotService;
        this.remotePointService = remotePointService;
        this.world = new World();
        this.shootingService = new ShootingService(this.world);
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
        LOGGER.info("[GameMechanicsImpl: getNewSpawnPoint] START serId: " + userId);

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

        addTankSnapshot(userId, tankSnap);

        try {
            this.remotePointService.sendMessageToUser(userId, spawnSnap);
        } catch(IOException ex) {
            LOGGER.error("[GameMechanicsImpl: getNewSpawnPoint] IOException: ", ex);
        } catch (Exception ex) {
            LOGGER.error("[GameMechanicsImpl: getNewSpawnPoint] Exception: ", ex);
        }
        LOGGER.info("[GameMechanicsImpl: getNewSpawnPoint] END: " + userId);
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
        shootingService.process(tankSnapshots, shootingLines);
        serverSnapshotService.send(tankSnapshots);
        tankSnapshotsService.reset();
    }
}
