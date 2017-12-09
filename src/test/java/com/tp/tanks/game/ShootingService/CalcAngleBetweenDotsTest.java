package com.tp.tanks.game.ShootingService;


import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.internal.ShootingService;
import com.tp.tanks.mechanics.world.World;
import com.tp.tanks.mocks.MockRemotePointService;
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
public class CalcAngleBetweenDotsTest {

    private final MockRemotePointService mockRemotePointService = new MockRemotePointService();
    private final ShootingService shootingService = new ShootingService(new World(), mockRemotePointService);

    private static final double DELTA = 1e-15;


    @Test
    public void serverAngleMustBe0() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, 0.D);

        Double serverAngleDeg = 0.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe45() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, -1000.D);

        Double serverAngleDeg = 45.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe90() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(0.D, -1000.D);

        Double serverAngleDeg = 90.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe135() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, -1000.D);

        Double serverAngleDeg = 135.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe180() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, 0.D);

        Double serverAngleDeg = 180.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe225() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, 1000.D);

        Double serverAngleDeg = 225.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe270() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(0.D, 1000.D);

        Double serverAngleDeg = 270.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

    @Test
    public void serverAngleMustBe315() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, 1000.D);

        Double serverAngleDeg = 315.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        Double angle = shootingService.calcAngleBetweenDots(first, second);
        Assert.assertEquals(serverAngleRad, angle, DELTA);
    }

}
