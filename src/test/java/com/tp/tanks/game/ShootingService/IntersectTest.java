package com.tp.tanks.game.ShootingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.tanks.factories.TankSnapFactory;
import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ShootingService;
import com.tp.tanks.mechanics.world.World;
import com.tp.tanks.mocks.MockRemotePointService;
import com.tp.tanks.websocket.RemotePointService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class IntersectTest {

    @Autowired
    private MockRemotePointService mockRemotePointService;
    private final ShootingService shootingService = new ShootingService(new World(), mockRemotePointService);

    private static final double STEP = 0.01;

    private final Coordinate shooterCoordinate = new Coordinate(0.D, 0.D);
    private final Coordinate enemyCoordinate = new Coordinate(1000.D, 1000.D);
    private final Double clientAngleBetweenDots = 45.D;
    private final Double distanceBetweenDots = shootingService.calcDistanceBetweenDots(shooterCoordinate, enemyCoordinate);
    private final Double dPhi = shootingService.calcDeltaPhi(distanceBetweenDots, 32.D);

    private TankSnap enemy;
    private Line line;

    @Before
    public void setUp() {
        TankSnap shooter = TankSnapFactory.createOneForUser(1L);
        enemy = TankSnapFactory.createOneForUser(2L);
        shooter.setPlatform(shooterCoordinate);
        enemy.setPlatform(enemyCoordinate);

        shooter.setTurretAngle(clientAngleBetweenDots);
        line = shooter.toLine();
    }

    @Test
    public void IntersectMustInCenter() {

        Boolean ok = shootingService.isIntersect(line, enemy.getPlatform());
        Assert.assertEquals(true, ok);
    }

    @Test
    public void IntersectMustInRight() {

        line.setServerAngleRad(line.getServerAngleRad() + dPhi);

        Boolean ok = shootingService.isIntersect(line, enemy.getPlatform());
        Assert.assertEquals(true, ok);
    }

    @Test
    public void IntersectMustInLeft() {

        line.setServerAngleRad(line.getServerAngleRad() - dPhi);

        Boolean ok = shootingService.isIntersect(line, enemy.getPlatform());
        Assert.assertEquals(true, ok);
    }


    @Test
    public void NotRightIntersect() {

        line.setServerAngleRad(line.getServerAngleRad() + dPhi + STEP);

        Boolean ok = shootingService.isIntersect(line, enemy.getPlatform());
        Assert.assertEquals(false, ok);
    }

    @Test
    public void NotLeftIntersect() {

        line.setServerAngleRad(line.getServerAngleRad() - dPhi - STEP);

        Boolean ok = shootingService.isIntersect(line, enemy.getPlatform());
        Assert.assertEquals(false, ok);
    }
}
