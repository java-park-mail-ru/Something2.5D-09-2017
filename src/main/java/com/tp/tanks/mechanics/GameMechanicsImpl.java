package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
public class GameMechanicsImpl implements GameMechanics {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    @NotNull
    private final TankSnapshotService tankSnapshotsService;


    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final ServerSnapshotService serverSnapshotService;

    @NotNull
    private ConcurrentLinkedQueue<Long> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(@NotNull TankSnapshotService tankSnapshotsService,
                             @NotNull ServerSnapshotService serverSnapshotService) {
        this.tankSnapshotsService = tankSnapshotsService;
        this.serverSnapshotService = serverSnapshotService;
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
                    LOGGER.error("Can't handle game task", ex);
                }
            }
        }

        final List<TankSnap> tankSnapshots = tankSnapshotsService.processSnapshots();
        serverSnapshotService.send(tankSnapshots);
        tankSnapshotsService.reset();
    }
}
