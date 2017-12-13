package com.tp.tanks.services;

import com.tp.tanks.factories.ScoreFactory;
import com.tp.tanks.factories.UserFactory;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.models.Statistic;
import com.tp.tanks.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Transactional
@ActiveProfiles("test")
public class StatisticServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void saveStatisticTest() {
        User user = UserFactory.create();
        User savedUser = userService.save(user);

        Scores scores = ScoreFactory.create();
        scores.setUsername(savedUser.getUsername());

        statisticsService.saveStatistics(savedUser.getId(), scores);

        Statistic statistic = statisticsService.statistic(savedUser.getId());
        Assert.assertEquals(scores.getKills(), statistic.getKills());
        Assert.assertEquals(scores.getDeaths(), statistic.getDeaths());
        Assert.assertEquals(scores.getMaxKills(), statistic.getMaxKills());
        Assert.assertEquals(scores.getUsername(), statistic.getUsername());
    }
}
