package com.tp.tanks.repository;

import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.models.Statistic;
import com.tp.tanks.services.StatisticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        jdbcTemplate.update(sql, new Object[]{tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills(), userId});
    }

    public void updateWithoutMaxKills(Long userId, Scores tankStatistics) {
        String sql = "UPDATE statistic_tbl SET kills=kills+?, deaths=deaths+? WHERE userid=?;";
        jdbcTemplate.update(sql, new Object[]{tankStatistics.getKills(), tankStatistics.getDeaths(), userId});
    }

    public void insert(Long userId, Scores tankStatistics) {
        String sql = "INSERT INTO statistic_tbl (userid, kills, deaths, maxkills) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, new Object[]{userId, tankStatistics.getKills(), tankStatistics.getDeaths(), tankStatistics.getMaxKills()});
    }

    public List<Statistic> getTop(Integer limit) {
        String sql = "SELECT s.id, s.userid, s.kills, s.deaths, s.maxkills, "
                + "ROW_NUMBER() OVER (ORDER BY kills DESC) AS position, u.username "
                + "FROM statistic_tbl s "
                + "JOIN user_tbl u ON u.id=s.userid "
                + "ORDER BY kills DESC LIMIT ?;";
        return jdbcTemplate.query(sql,  new Object[]{limit}, new StatisticMapper());
    }

    public Statistic position(Long userId) {
        String sql = "SELECT s.id, s.userid, s.kills, s.deaths, s.maxkills, u.username, "
                + "(SELECT tmp.position "
                + "from (select s.userid, ROW_NUMBER() OVER (ORDER BY kills DESC) AS position "
                + "from statistic_tbl s "
                + "ORDER BY s.kills DESC) tmp "
                + "WHERE tmp.userid=? "
                + ") AS position "
                + "FROM statistic_tbl s "
                + "JOIN user_tbl u ON u.id=s.userid "
                + "WHERE userId=?;";
        return jdbcTemplate.queryForObject(sql,  new Object[]{userId, userId}, new StatisticMapper());
    }
}
