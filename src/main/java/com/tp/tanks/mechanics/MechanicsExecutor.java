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

    @NotNull
    private Clock clock = Clock.systemDefaultZone();

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        try {
            mainCycle();
        } finally {
            LOGGER.warn("Mechanic executor terminated");
        }
    }

    private void mainCycle() {
        long lastFrameMillis = STEP_TIME;
        while (true) {
            LOGGER.info("In main cycle");
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.error("Mechanics thread was interrupted", e);
                }

                if (Thread.currentThread().isInterrupted()) {
                    LOGGER.error("Mechanics thread was interrupted");
                    return;
                }

            } catch (RuntimeException e) {
                LOGGER.error("Mechanics executor was reseted due to exception", e);
            }
        }
    }
}
