package com.tp.tanks.mechanics;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class MechanicsExecutor implements Runnable {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicsExecutor.class);
    private static final long STEP_TIME = 50;
    private final GameMechanics gameMechanics;

    private boolean isExecute = true;

    @NotNull
    private Clock clock = Clock.systemDefaultZone();

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Autowired
    public MechanicsExecutor(@NotNull GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    @Override
    public void run() {
        try {
            mainCycle();
        } finally {
            LOGGER.warn("Mechanic executor terminated");
        }
    }

    public void lock() {
        isExecute = false;
    }

    public void unlock() {
        isExecute = true;
    }

    private void mainCycle() {
        long lastFrameMillis = STEP_TIME;
        while (true) {

            if (!Thread.interrupted()) {

                try {
                    if (isExecute) {
                        final long before = clock.millis();

                        gameMechanics.gmStep(lastFrameMillis);

                        final long after = clock.millis();

                        final long sleepingTime = Math.max(0, STEP_TIME - (after - before));
                        Thread.sleep(sleepingTime);

                        final long afterSleep = clock.millis();
                        lastFrameMillis = afterSleep - before;
                    } else {
                        LOGGER.info("[MechanicsExecutor.mainCycle] waiting for unlock...");
                        Thread.sleep(STEP_TIME);
                    }

                } catch (InterruptedException e) {
                    LOGGER.error("Mechanics thread was interrupted", e);

                } catch (RuntimeException e) {
                    LOGGER.error("Mechanics executor was reseted due to exception", e);
                }

            } else {
                LOGGER.error("Mechanics thread was interrupted");
                return;
            }
        }
    }
}
