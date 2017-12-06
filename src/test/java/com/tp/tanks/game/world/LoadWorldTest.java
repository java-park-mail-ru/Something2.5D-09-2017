package com.tp.tanks.game.world;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.world.Box;
import com.tp.tanks.mechanics.world.Loader;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoadWorldTest {

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadWorldTest.class);

    @Test
    public void loadBoxesTest() {

        List<Box> boxes = Loader.loadBoxes();
        Assert.assertNotNull(boxes);
        Assert.assertNotEquals(0, boxes.size());

        for(Box box: boxes) {
            LOGGER.info("Box = " + box.toString());
        }
    }

    @Test
    public void loadSpawnPointsTest() {

        List<Coordinate> spawnPoints = Loader.loadSpawnPoints();
        Assert.assertNotNull(spawnPoints);
        Assert.assertNotEquals(0, spawnPoints.size());

        for(Coordinate coordinate: spawnPoints) {
            LOGGER.info("SpawnPoint = " + coordinate.toString());
        }
    }
}
