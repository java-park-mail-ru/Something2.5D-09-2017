package com.tp.tanks.mocks;

import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class MockServerSnapshotService extends ServerSnapshotService {

    private int numbProcessedSnapshots;

    public MockServerSnapshotService(@NotNull RemotePointService remotePointService) {
        super(remotePointService);
    }

    @Override
    public void send(List<TankSnap> tanks) {
        numbProcessedSnapshots = tanks.size();
    }

    public int getNumbProcessedSnapshots() {
        return numbProcessedSnapshots;
    }
}
