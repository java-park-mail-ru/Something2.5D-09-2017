package com.tp.tanks.game.line;

import com.tp.tanks.factories.TankSnapFactory;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
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
public class LineConvertingTest {

    private static final double DELTA = 1e-15;

    @Test
    public void firstQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);
        double angle = 45.D;
        snap.setTurretAngle(angle);
        Line line = snap.toLine();

        Assert.assertEquals(Math.toRadians(angle), line.getAngleRad(), DELTA);
        Assert.assertEquals(1.D, line.getKoefK(), DELTA);
    }

    @Test
    public void secondQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);
        double angle = 135;
        snap.setTurretAngle(angle);
        Line line = snap.toLine();

        Assert.assertEquals(Math.toRadians(angle), line.getAngleRad(), DELTA);
        Assert.assertEquals(-1.D, line.getKoefK(), DELTA);
    }

    @Test
    public void thirdQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);
        double absoluteAngle = 225;
        double angle = -135;
        snap.setTurretAngle(angle);
        Line line = snap.toLine();

        Assert.assertEquals(Math.toRadians(absoluteAngle), line.getAngleRad(), DELTA);
        Assert.assertEquals(1.D, line.getKoefK(), DELTA);
    }


    @Test
    public void fourthQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);
        double absoluteAngle = 315;
        double angle = -45;
        snap.setTurretAngle(angle);
        Line line = snap.toLine();

        Assert.assertEquals(Math.toRadians(absoluteAngle), line.getAngleRad(), DELTA);
        Assert.assertEquals(-1.D, line.getKoefK(), DELTA);
    }
}
