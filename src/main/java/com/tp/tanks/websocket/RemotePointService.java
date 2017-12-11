package com.tp.tanks.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.mechanics.world.ScoresToSend;
import com.tp.tanks.services.StatisticsService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class RemotePointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemotePointService.class);
    private Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Set<Long> players = new ConcurrentSkipListSet<>();
    private final ObjectMapper objectMapper;
    private Map<Long, Scores> tanksStats;
    private StatisticsService statisticsService;

    private final Comparator<ScoresToSend> scoresToSendComparator = (score1, score2) -> (int) (score2.getKills() - score1.getKills());

    @Autowired
    public RemotePointService(ObjectMapper objectMapper, StatisticsService statisticsService) {
        this.objectMapper = objectMapper;
        this.tanksStats = new ConcurrentHashMap<>();
        this.statisticsService = statisticsService;
    }

    public void registerUser(@NotNull Long userId, @NotNull String username, @NotNull WebSocketSession webSocketSession) {
        LOGGER.info("[RemotePointService.registerUser] register userID = " + userId.toString());
        sessions.put(userId, webSocketSession);
        players.add(userId);
        tanksStats.put(userId, new Scores(username));
    }

    public boolean isConnected(@NotNull Long userId) {
        return sessions.containsKey(userId) && sessions.get(userId).isOpen() && players.contains(userId);
    }

    public List<ScoresToSend> getTopPlayers(int amount) {
        List<ScoresToSend> top = new ArrayList<>();

        for (Map.Entry<Long, Scores> entry : tanksStats.entrySet()) {
            Long userId = entry.getKey();
            Scores scores = entry.getValue();
            if (top.size() <= amount) {
                ScoresToSend scoresToSend = new ScoresToSend(userId, scores.getUsername(), scores.getKills());
                top.add(scoresToSend);
            } else {
                ScoresToSend min = top.parallelStream().min(scoresToSendComparator).get();
                if (scores.getKills() > min.getKills()) {
                    min.setUserId(userId);
                    min.setUsername(scores.getUsername());
                    min.setKills(scores.getKills());
                }
            }
        }
        top.sort(scoresToSendComparator);
        return top;
    }

    public Set<Long> getPlayers() {
        return players;
    }

    public Map<Long, Scores> getTanksStats() {
        return tanksStats;
    }

    public Scores getTanksStatsForUser(Long userId) {
        return tanksStats.get(userId);
    }

    public void saveStatistics(@NotNull Long userId) {
        statisticsService.saveStatistics(userId, tanksStats.get(userId));
    }

    public void removeUser(@NotNull Long userId) {
        sessions.remove(userId);
        players.remove(userId);
        tanksStats.remove(userId);
        LOGGER.info("[RemotePointService.removeUser] unregister userID = " + userId.toString());
    }

    public void killUser(@NotNull Long userId) {
        players.remove(userId);
        tanksStats.get(userId).incrementDeaths();
        LOGGER.info("[RemotePointService.killUser] userID = " + userId.toString());
    }

    public void incrementKills(@NotNull Long userId) {
        this.tanksStats.get(userId).incrementKills();
    }

    public void spawnUser(@NotNull Long userId) {
        players.add(userId);
        LOGGER.info("[RemotePointService.spawnUser] userID = " + userId.toString());
    }

    public void removeAlluses() {
        sessions.clear();
        players.clear();
    }

    public void cutDownConnection(@NotNull Long userId, @NotNull CloseStatus closeStatus) {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close(closeStatus);
            } catch (IOException ignore) {
                LOGGER.error("Can't close session");
            }
        }
    }

    public void sendMessageToUser(@NotNull Long userId, @NotNull Message message) throws IOException {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession == null) {
            LOGGER.error("[RemotePointService.sendMessageToUser] webSocketSession is null");
            throw new IOException("no game websocket for user " + userId);
        }
        if (!webSocketSession.isOpen()) {
            LOGGER.error("[RemotePointService.sendMessageToUser] session is closed or not exsists");
            throw new IOException("session is closed or not exsists");
        }
        //noinspection OverlyBroadCatchBlock
        try {
            //noinspection ConstantConditions

            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            throw new IOException("Unnable to send message", e);
        }
    }
}
