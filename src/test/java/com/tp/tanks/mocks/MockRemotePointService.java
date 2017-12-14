package com.tp.tanks.mocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.services.StatisticsService;
import com.tp.tanks.websocket.Message;
import com.tp.tanks.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MockRemotePointService extends RemotePointService {

    private ServerSnap sendingServerSnap;

    @Autowired
    public MockRemotePointService(StatisticsService statisticsService) {
        super(new ObjectMapper(), statisticsService);
    }

    @Override
    public void sendMessageToUser(@NotNull Long userId, @NotNull Message message) throws IOException {
        if (message instanceof ServerSnap) {
            sendingServerSnap = (ServerSnap) message;
        }
    }

    public void pushUser(Long userId, String username) {
        super.getPlayers().add(userId);
        super.getTanksStats().put(userId, new Scores(username));
    }

    public ServerSnap getSendingServerSnap() {
        return sendingServerSnap;
    }
}
