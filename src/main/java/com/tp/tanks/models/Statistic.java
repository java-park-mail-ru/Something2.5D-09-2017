package com.tp.tanks.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "statistic_tbl")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="userid")
    private Long userId;
    @Column(name="kills")
    private Integer kills;
    @Column(name="deaths")
    private Integer deaths;
    @Column(name="maxkills")
    private Integer maxKills;
    @Transient
    private Integer position;
    @Transient
    private String username;

    @JsonCreator
    public Statistic() {
    }

    public Statistic(Long userId, Integer kills, Integer deaths, Integer maxKills) {
        this.userId = userId;
        this.kills = kills;
        this.deaths = deaths;
        this.maxKills = maxKills;
    }

    public Statistic(Long userId, Integer kills, Integer deaths, Integer maxKills, Integer position, String username) {
        this.userId = userId;
        this.kills = kills;
        this.deaths = deaths;
        this.maxKills = maxKills;
        this.position = position;
        this.username = username;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONObject getJson() throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("kills", kills);
        jsonObject.put("deaths", deaths);
        jsonObject.put("maxKills", maxKills);
        jsonObject.put("username", username);
        jsonObject.put("position", position);
        return jsonObject;
    }

    public static JSONArray getJsonArray(List<Statistic> statistics) throws JSONException {
        final JSONArray arr = new JSONArray();
        for (Statistic s : statistics) {
            JSONObject obj = s.getJson();
            arr.put(obj);
        }
        return arr;
    }
}
