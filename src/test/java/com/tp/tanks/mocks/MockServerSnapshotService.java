package com.tp.tanks.mocks;

import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;


import java.util.List;
import java.util.Map;

public class MockServerSnapshotService extends ServerSnapshotService {

    private int numbSendSnapshots;

    public MockServerSnapshotService(@NotNull RemotePointService remotePointService) {
        super(remotePointService);
    }

    @Override
    public void send(List<TankSnap> tanks, Map<Long, Scores> tanksStats) {
        numbSendSnapshots = tanks.size();
    }

    public int getNumbSendSnapshots() {
        return numbSendSnapshots;
    }
}
