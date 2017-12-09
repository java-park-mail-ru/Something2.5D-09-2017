package com.tp.tanks.game.world;

import com.tp.tanks.mechanics.world.TankStatistics;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TanksStatisticsTest {

    @Test
    public void incrementKillsAndDeath() {
        TankStatistics tankStatistics = new TankStatistics();

        for(int i = 0; i < 9; ++i) {
            tankStatistics.incrementKills();
            tankStatistics.incrementDeaths();
        }

        Assert.assertEquals(tankStatistics.getKills(), Integer.valueOf(9));
        Assert.assertEquals(tankStatistics.getDeaths(), Integer.valueOf(9));
    }

    @Test
    public void maxKillsWithoutDeath() {
        TankStatistics tankStatistics = new TankStatistics();

        for(int i = 0; i < 5; ++i) {
            tankStatistics.incrementKills();
        }
        tankStatistics.incrementDeaths();

        for(int i = 0; i < 6; ++i) {
            tankStatistics.incrementKills();
        }
        tankStatistics.incrementDeaths();

        for(int i = 0; i < 4; ++i) {
            tankStatistics.incrementKills();
        }

        Assert.assertEquals((long)tankStatistics.getKills(), 15l);
        Assert.assertEquals((long)tankStatistics.getDeaths(), 2l);
        Assert.assertEquals((long)tankStatistics.getMaxKills(), 6l);
    }
}
