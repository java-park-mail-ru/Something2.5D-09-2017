package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.ShootingService;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


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

    public GameMechanicsImpl(@NotNull TankSnapshotService tankSnapshotsService,
                             @NotNull ServerSnapshotService serverSnapshotService) {
        this.tankSnapshotsService = tankSnapshotsService;
        this.serverSnapshotService = serverSnapshotService;
        this.shootingService = new ShootingService();
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
//        LOGGER.info("[GMSTEP] START");
//        for(int i = 0; i < tankSnapshots.size(); ++i) {
//            LOGGER.info("[gmStep BEFORE]: player number: " + tankSnapshots.get(i).getUserId() + "  health: " + tankSnapshots.get(i).getHealth());
//        }
        List<Line> shootingLines = tankSnapshotsService.shootingLines();
        shootingService.process(tankSnapshots, shootingLines);
        serverSnapshotService.send(tankSnapshots);

//        for(int i = 0; i < tankSnapshots.size(); ++i) {
//            LOGGER.info("[gmStep AFTER]: player number: " + tankSnapshots.get(i).getUserId() + "  health: " + tankSnapshots.get(i).getHealth());
//        }
//        LOGGER.info("[GMSTEP] END");

        tankSnapshotsService.reset();
    }
}
