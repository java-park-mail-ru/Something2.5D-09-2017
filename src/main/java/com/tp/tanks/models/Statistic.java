package com.tp.tanks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "statistic_tbl")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Long userId;
    private Integer kills;
    private Integer deaths;
    private Integer maxKills;

    public Statistic() {
    }

    public Statistic(Long userId, Integer kills, Integer deaths, Integer maxKills) {
        this.userId = userId;
        this.kills = kills;
        this.deaths = deaths;
        this.maxKills = maxKills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
