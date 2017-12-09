package com.tp.tanks.services;

import com.tp.tanks.mechanics.world.TankStatistics;
import com.tp.tanks.repository.StatisticsRepository;
import com.tp.tanks.models.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public StatisticsService() {
    }

    public void saveStatistics(Long userId, TankStatistics tankStatistics) {
        Statistic statistic = statisticsRepository.getById(userId);
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
        }
    }

}
