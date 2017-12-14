package com.tp.tanks.services;

import com.tp.tanks.models.Statistic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticMapper implements RowMapper<Statistic> {
    @Override
    public Statistic mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Statistic statistic = new Statistic();
        statistic.setId(rs.getLong("id"));
        statistic.setUserId(rs.getLong("userId"));
        statistic.setKills(rs.getInt("kills"));
        statistic.setDeaths(rs.getInt("deaths"));
        statistic.setMaxKills(rs.getInt("maxKills"));
        try {
            statistic.setPosition(rs.getInt("position"));
        } catch (Exception ex) {
            statistic.setPosition(null);
        }
        try {
            statistic.setUsername(rs.getString("username"));
        } catch (Exception ex) {
            statistic.setUsername(null);
        }
        return statistic;
    }
}
