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

        Double clientAngleDeg = -45.D;
        Double serverAngleDeg = 45.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        snap.setTurretAngle(clientAngleDeg);
        Line line = snap.toLine();

        Assert.assertEquals(clientAngleDeg, line.getClientAngleDeg(), DELTA);
        Assert.assertEquals(serverAngleRad, line.getServerAngleRad(), DELTA);
    }

    @Test
    public void secondQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);

        Double clientAngleDeg = -135.D;
        Double serverAngleDeg = 135.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        snap.setTurretAngle(clientAngleDeg);
        Line line = snap.toLine();

        Assert.assertEquals(clientAngleDeg, line.getClientAngleDeg(), DELTA);
        Assert.assertEquals(serverAngleRad, line.getServerAngleRad(), DELTA);
    }

    @Test
    public void thirdQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);

        Double clientAngleDeg = 135.D;
        Double serverAngleDeg = 225.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        snap.setTurretAngle(clientAngleDeg);
        Line line = snap.toLine();

        Assert.assertEquals(clientAngleDeg, line.getClientAngleDeg(), DELTA);
        Assert.assertEquals(serverAngleRad, line.getServerAngleRad(), DELTA);
    }


    @Test
    public void fourthQuarterDegreeAngleTest() {
        TankSnap snap = TankSnapFactory.createOneForUser(null);

        Double clientAngleDeg = 45.D;
        Double serverAngleDeg = 315.D;
        Double serverAngleRad = Math.toRadians(serverAngleDeg);

        snap.setTurretAngle(clientAngleDeg);
        Line line = snap.toLine();

        Assert.assertEquals(clientAngleDeg, line.getClientAngleDeg(), DELTA);
        Assert.assertEquals(serverAngleRad, line.getServerAngleRad(), DELTA);
    }
}
