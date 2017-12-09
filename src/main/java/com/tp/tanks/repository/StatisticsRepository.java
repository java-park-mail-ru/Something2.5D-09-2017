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
        String sql = "SELECT * FROM statistic_tbl WHERE userId=?;";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new StatisticMapper());
    }

    public void update(Long userId, Scores tankStatistics) {
        String sql = "UPDATE statistic_tbl SET kills=kills+?, deaths=deaths+?, maxKills=? WHERE userId=?;";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills()});
    }

    public void updateWithoutMaxKills(Long userId, Scores tankStatistics) {
        String sql = "UPDATE statistic_tbl SET kills=kills+?, deaths=deaths+? WHERE userId=?;";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths()});
    }

    public void insert(Long userId, Scores tankStatistics) {
        String sql = "INSERT INTO statistic_tbl (userId, kills, deaths, maxKills) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills()});
    }
}
