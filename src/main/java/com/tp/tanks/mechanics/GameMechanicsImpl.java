package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.TankSnapshotService;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;


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

    @NotNull
    private ConcurrentLinkedQueue<Long> waiters = new ConcurrentLinkedQueue<>();


    public GameMechanicsImpl(@NotNull TankSnapshotService tankSnapshotsService) {
        this.tankSnapshotsService = tankSnapshotsService;
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
        LOGGER.info("GameMechanics step...");
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

        tankSnapshotsService.processSnapshots();
        tankSnapshotsService.reset();
    }
}
