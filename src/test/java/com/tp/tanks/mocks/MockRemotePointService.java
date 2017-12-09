package com.tp.tanks.mocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.world.TankStatistics;
import com.tp.tanks.websocket.Message;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MockRemotePointService extends RemotePointService {

    private ServerSnap sendingServerSnap;

    public MockRemotePointService() {
        super(new ObjectMapper());
    }

    @Override
    public void sendMessageToUser(@NotNull Long userId, @NotNull Message message) throws IOException {
        if (message instanceof ServerSnap) {
            sendingServerSnap = (ServerSnap) message;
        }
    }

    public void pushUser(Long userId) {
        super.getPlayers().add(userId);
        super.getTanksStats().put(userId, new TankStatistics());
    }

    public ServerSnap getSendingServerSnap() {
        return sendingServerSnap;
    }
}
