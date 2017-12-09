package com.tp.tanks.mechanics.world;

public class TankStatistics {
    private Integer kills;
    private Integer deaths;
    private Integer maxKills;
    private Integer currentKills;

    public TankStatistics() {
    }

    public TankStatistics(Integer kills, Integer deaths, Integer maxKills, Integer currentKills) {
        this.kills = kills;
        this.deaths = deaths;
        this.maxKills = maxKills;
        this.currentKills = currentKills;
    }

    public void incrementDeaths() {
        this.deaths++;
        this.currentKills = 0;
    }

    public void incrementKills() {
        this.kills++;
        this.currentKills++;
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
}
