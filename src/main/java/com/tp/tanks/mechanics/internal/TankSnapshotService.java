package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.TankSnap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TankSnapshotService {

    private final Map<Long, List<TankSnap>> snapsMap = new HashMap<>();

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(TankSnapshotService.class);

    public void pushTankSnap(@NotNull Long userId, @NotNull TankSnap snap) {

        this.snapsMap.putIfAbsent(userId, new ArrayList<>());
        final List<TankSnap> clientSnaps = snapsMap.get(userId);
        clientSnaps.add(snap);
    }

    @NotNull
    public List<TankSnap> getSnapForUser(@NotNull Long userId) {
        return snapsMap.getOrDefault(userId, Collections.emptyList());
    }


    public List<TankSnap> processSnapshots() {

        List<TankSnap> lastSnapshots = new ArrayList<>();

        for (Map.Entry<Long, List<TankSnap>> entry : snapsMap.entrySet()) {
            final List<TankSnap> snaps = entry.getValue();
            if (snaps.isEmpty()) {
                continue;
            }

            final TankSnap lastSnap = snaps.get(snaps.size() - 1);
            lastSnapshots.add(lastSnap);
        }

        return lastSnapshots;
    }

    public List<TankSnap> shootingSnapshots() {
        List<TankSnap> shootingSnapshots = new ArrayList<>();

        for (Map.Entry<Long, List<TankSnap>> entry : snapsMap.entrySet()) {
            final List<TankSnap> snaps = entry.getValue();
            if (snaps.isEmpty()) {
                continue;
            }

//            final TankSnap lastSnap = snaps.parallelStream().filter(snap -> snap.isShoot() == true);
//            shootingSnapshots.add(lastSnap);
        }

        return shootingSnapshots;
    }

    public void clearForUser(Long userId) {
        snapsMap.remove(userId);
    }

    public void reset() {
        snapsMap.clear();
    }
}
