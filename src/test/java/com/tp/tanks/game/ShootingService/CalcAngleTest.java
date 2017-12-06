package com.tp.tanks.game.ShootingService;


import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.internal.ShootingService;
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
public class CalcAngleTest {

    private final ShootingService shootingService = new ShootingService();

    private static final double DELTA = 1e-15;


    @Test
    public void angleMustBe0() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, 0.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(0), angle, DELTA);
    }

    @Test
    public void angleMustBe45() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, 1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(45.D), angle, DELTA);
    }

    @Test
    public void angleMustBe90() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(0.D, 1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(90.D), angle, DELTA);
    }

    @Test
    public void angleMustBe135() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, 1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(135.D), angle, DELTA);
    }

    @Test
    public void angleMustBe180() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, 0.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(180.D), angle, DELTA);
    }

    @Test
    public void angleMustBe225() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(-1000.D, -1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(225.D), angle, DELTA);
    }

    @Test
    public void angleMustBe270() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(0.D, -1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(270.D), angle, DELTA);
    }

    @Test
    public void angleMustBe315() {

        Coordinate first = new Coordinate(0.D, 0.D);
        Coordinate second = new Coordinate(1000.D, -1000.D);

        Double angle = shootingService.calcAngle(first, second);
        Assert.assertEquals(Math.toRadians(315.D), angle, DELTA);
    }

}
