package com.tp.tanks.mechanics.world;

public class Scores {

    private Integer kills;
    private Integer deaths;
    private Integer maxKills;
    private Integer currentKills;
    private String username;

    public Scores(String username) {
        kills = 0;
        deaths = 0;
        maxKills = 0;
        currentKills = 0;
        this.username = username;
    }

    public void incrementDeaths() {
        this.deaths++;
        this.currentKills = 0;
    }

    public void incrementKills() {
        this.kills++;
        this.currentKills++;
        if (currentKills > maxKills) {
            maxKills = currentKills;
        }
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getMaxKills() {
        return maxKills;
    }

    public void setMaxKills(Integer maxKills) {
        this.maxKills = maxKills;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
