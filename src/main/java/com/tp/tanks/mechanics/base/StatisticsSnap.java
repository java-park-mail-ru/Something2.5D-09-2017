package com.tp.tanks.mechanics.base;

public class StatisticsSnap {

    private Long userId;
    private String username;

    private Integer kills;

    public StatisticsSnap() {
    }

    public StatisticsSnap(Long userId, String username, Integer kills) {
        this.userId = userId;
        this.username = username;
        this.kills = kills;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }
}
