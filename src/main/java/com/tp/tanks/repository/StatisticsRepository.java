package com.tp.tanks.repository;

import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.models.Statistic;
import com.tp.tanks.services.StatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Statistic getById(Long userId) {
        String sql = "SELECT * FROM statistic_tbl WHERE userid=?;";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new StatisticMapper());
    }

    public void update(Long userId, Scores tankStatistics) {
        String sql = "UPDATE statistic_tbl SET kills=kills+?, deaths=deaths+?, maxkills=? WHERE userid=?;";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills()});
    }

    public void updateWithoutMaxKills(Long userId, Scores tankStatistics) {
        String sql = "UPDATE statistic_tbl SET kills=kills+?, deaths=deaths+? WHERE userid=?;";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths()});
    }

    public void insert(Long userId, Scores tankStatistics) {
        String sql = "INSERT INTO statistic_tbl (userid, kills, deaths, maxkills) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills()});
    }

    public Statistic getTop(Integer limit) {
        String sql = "SELECT * FROM statistic_tbl ORDER BY kills LIMIT ?;";
        return jdbcTemplate.queryForObject(sql,  new Object[]{limit}, new StatisticMapper());
    }
}
