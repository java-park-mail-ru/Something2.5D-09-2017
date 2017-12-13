package com.tp.tanks.services;

import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.repository.StatisticsRepository;
import com.tp.tanks.models.Statistic;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);

    public StatisticsService() {
    }

    public void saveStatistics(Long userId, Scores tankStatistics) {
        Statistic statistic = null;
        try {
            statistic = statisticsRepository.getById(userId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        try {
            if (statistic != null) {

                if (tankStatistics.getMaxKills() > statistic.getMaxKills()) {
                    statisticsRepository.update(userId, tankStatistics);
                } else {
                    statisticsRepository.updateWithoutMaxKills(userId, tankStatistics);
                }
            } else {
                statisticsRepository.insert(userId, tankStatistics);
            }
        } catch (Exception ex) {
            LOGGER.info("[StatisticsService:saveStatistics] database exception: " + ex);
        }
    }

    public List<Statistic> getTop(Integer limit) {
        List<Statistic> response = null;
        try {
            response = statisticsRepository.getTop(limit);
        } catch (Exception ex) {
            LOGGER.info("[StatisticsService:getTop] database exception: " + ex);
        }
        return response;
    }

    public Statistic position(Long userId) {
        Statistic response = null;
        try {
            response = statisticsRepository.position(userId);
        } catch (Exception ex) {
            LOGGER.info("[StatisticsService:position] database exception: " + ex);
        }
        return response;
    }
}
