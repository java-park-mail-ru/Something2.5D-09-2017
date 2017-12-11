package com.tp.tanks.game.ShootingService;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.internal.ShootingService;
import com.tp.tanks.mechanics.world.World;
import com.tp.tanks.mocks.MockRemotePointService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@SuppressWarnings({"MagicNumber", "NullableProblems", "SpringJavaAutowiredMembersInspection"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class GetClosestTankTest {

    @Autowired
    private MockRemotePointService mockRemotePointService;
    private final ShootingService shootingService = new ShootingService(new World(), mockRemotePointService);

    private static final double DELTA = 1e-15;

    @Test
    public void closestTankRightDirection() {
        Coordinate center = new Coordinate(960.D, 540.D);
        Line line = new Line(1L, center, 0.D);

        ArrayList<TankSnap> snaps = new ArrayList<>();

        TankSnap third = new TankSnap();
        third.setPlatform(new Coordinate(1260.D, 540.D));
        snaps.add(third);

        TankSnap second = new TankSnap();
        second.setPlatform(new Coordinate(1160.D, 540.D));
        snaps.add(second);

        TankSnap first = new TankSnap();
        first.setPlatform(new Coordinate(1060.D, 540.D));
        snaps.add(first);

        TankSnap closestSnap = shootingService.getClosestTank(snaps, line);
        Assert.assertEquals(closestSnap.getPlatform().getValX(), first.getPlatform().getValX(), DELTA);
        Assert.assertEquals(closestSnap.getPlatform().getValY(), first.getPlatform().getValY(), DELTA);
    }

    @Test
    public void closestTankLeftDirection() {
        Coordinate center = new Coordinate(960.D, 540.D);
        Line line = new Line(1L, center, 0.D);

        ArrayList<TankSnap> snaps = new ArrayList<>();

        TankSnap third = new TankSnap();
        third.setPlatform(new Coordinate(660.D, 540.D));
        snaps.add(third);

        TankSnap second = new TankSnap();
        second.setPlatform(new Coordinate(760.D, 540.D));
        snaps.add(second);

        TankSnap first = new TankSnap();
        first.setPlatform(new Coordinate(860.D, 540.D));
        snaps.add(first);

        TankSnap closestSnap = shootingService.getClosestTank(snaps, line);
        Assert.assertEquals(closestSnap.getPlatform().getValX(), first.getPlatform().getValX(), DELTA);
        Assert.assertEquals(closestSnap.getPlatform().getValY(), first.getPlatform().getValY(), DELTA);
    }

    @Test
    public void closestTankUpDirection() {
        Coordinate center = new Coordinate(960.D, 540.D);
        Line line = new Line(1L, center, 0.D);

        ArrayList<TankSnap> snaps = new ArrayList<>();

        TankSnap third = new TankSnap();
        third.setPlatform(new Coordinate(960.D, 240.D));
        snaps.add(third);

        TankSnap second = new TankSnap();
        second.setPlatform(new Coordinate(960.D, 340.D));
        snaps.add(second);

        TankSnap first = new TankSnap();
        first.setPlatform(new Coordinate(960.D, 440.D));
        snaps.add(first);

        TankSnap closestSnap = shootingService.getClosestTank(snaps, line);
        Assert.assertEquals(closestSnap.getPlatform().getValX(), first.getPlatform().getValX(), DELTA);
        Assert.assertEquals(closestSnap.getPlatform().getValY(), first.getPlatform().getValY(), DELTA);
    }

    @Test
    public void closestTankDownDirection() {
        Coordinate center = new Coordinate(960.D, 540.D);
        Line line = new Line(1L, center, 0.D);

        ArrayList<TankSnap> snaps = new ArrayList<>();

        TankSnap third = new TankSnap();
        third.setPlatform(new Coordinate(960.D, 840.D));
        snaps.add(third);

        TankSnap second = new TankSnap();
        second.setPlatform(new Coordinate(960.D, 740.D));
        snaps.add(second);

        TankSnap first = new TankSnap();
        first.setPlatform(new Coordinate(960.D, 640.D));
        snaps.add(first);

        TankSnap closestSnap = shootingService.getClosestTank(snaps, line);
        Assert.assertEquals(closestSnap.getPlatform().getValX(), first.getPlatform().getValX(), DELTA);
        Assert.assertEquals(closestSnap.getPlatform().getValY(), first.getPlatform().getValY(), DELTA);
    }
}
