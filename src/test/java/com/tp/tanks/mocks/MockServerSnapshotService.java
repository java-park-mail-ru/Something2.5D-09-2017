package com.tp.tanks.mocks;

import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ServerSnapshotService;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class MockServerSnapshotService extends ServerSnapshotService {

    private int numbSendSnapshots;

    public MockServerSnapshotService(@NotNull RemotePointService remotePointService) {
        super(remotePointService);
    }

    @Override
    public void send(List<TankSnap> tanks) {
        numbSendSnapshots = tanks.size();
    }

    public int getNumbSendSnapshots() {
        return numbSendSnapshots;
    }
}
