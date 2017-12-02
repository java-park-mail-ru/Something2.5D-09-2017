package com.tp.tanks.mocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.base.ServerSnap;
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

    public ServerSnap getSendingServerSnap() {
        return sendingServerSnap;
    }
}
