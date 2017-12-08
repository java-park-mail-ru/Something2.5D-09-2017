package com.tp.tanks.mechanics.requests;

import com.tp.tanks.websocket.Message;

public class SpawnRequest extends Message {

    private String username;
    private Long userId;

    public SpawnRequest() {
    }

    public SpawnRequest(String username, Long userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
