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

//        LOGGER.info("[pushing TankSnap] {x: " + snap.getPlatform().X() + ", y: " +  snap.getPlatform().Y() + "}");
        this.snapsMap.putIfAbsent(userId, new ArrayList<>());
        final List<TankSnap> clientSnaps = snapsMap.get(userId);
        clientSnaps.add(snap);
    }

    @NotNull
    public List<TankSnap> getSnapForUser(@NotNull Long userId) {
        return snapsMap.getOrDefault(userId, Collections.emptyList());
    }


    public void processSnapshots() {
        for (Map.Entry<Long, List<TankSnap>> entry : snapsMap.entrySet()) {
            Long userId = entry.getKey();
            List<TankSnap> snaps = entry.getValue();
            if (snaps.isEmpty()) {
                continue;
            }

            TankSnap lastSnap = snaps.get(snaps.size() - 1);
            LOGGER.info("[process TankSnap] {userId: " + userId.toString() +
                    ", x: " + lastSnap.getPlatform().X() +
                    ", y: " + lastSnap.getPlatform().Y() +
                    "}");
        }
    }

//    public void processSnapshotsFor(@NotNull GameSession gameSession) {
//        final Collection<GameUser> players = new ArrayList<>();
//        players.add(gameSession.getFirst());
//        players.add(gameSession.getSecond());
//        for (GameUser player : players) {
//            final List<TankSnap> playerSnaps = getSnapForUser(player.getUserId());
//            if (playerSnaps.isEmpty()) {
//                continue;
//            }
//
//            playerSnaps.stream().filter(TankSnap::isFiring).findFirst().ifPresent(snap -> processClick(snap, gameSession, player));
//
//            final TankSnap lastSnap = playerSnaps.get(playerSnaps.size() - 1);
//            processMouseMove(player, lastSnap.getMouse());
//        }
//    }

//    private void processClick(@NotNull TankSnap snap, @NotNull GameSession gameSession, @NotNull GameUser gameUser) {
//        final MechanicPart mechanicPart = gameUser.claimPart(MechanicPart.class);
//        if (mechanicPart.tryFire()) {
//            gameSession.getBoard().fireAt(snap.getMouse());
//        }
//    }
//
//    private void processMouseMove(@NotNull GameUser gameUser, @NotNull Coords mouse) {
//        gameUser.claimPart(MousePart.class).setMouse(mouse);
//    }

    public void clearForUser(Long userId) {
        snapsMap.remove(userId);
    }

    public void reset() {
        snapsMap.clear();
    }
}
