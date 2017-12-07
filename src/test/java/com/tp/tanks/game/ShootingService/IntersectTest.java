package com.tp.tanks.game.ShootingService;

import com.tp.tanks.factories.TankSnapFactory;
import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
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
public class IntersectTest {

    private final ShootingService shootingService = new ShootingService();

    private static final double DELTA = 1e-15;

    private static final double STEP = 0.01;


    @Test
    public void IntersectMustInCenter() {

        TankSnap shooter = TankSnapFactory.createOneForUser(null);
        TankSnap enemy = TankSnapFactory.createOneForUser(null);

        shooter.setPlatform(new Coordinate(0.D, 0.D));
        enemy.setPlatform(new Coordinate(1000.D, 1000.D));
        Double clientTurretAngle = 45.D;

        shooter.setTurretAngle(clientTurretAngle);
        Line line = shooter.toLine();


        Boolean ok = shootingService.isIntersect(line, enemy);
        Assert.assertEquals(true, ok);
    }

    @Test
    public void IntersectMustInRight() {

        Coordinate shooterCoord = new Coordinate(0.D, 0.D);
        Coordinate enemyCoord = new Coordinate(1000.D, 1000.D);
        Double clientTurretAngle = 45.D;

        TankSnap shooter = TankSnapFactory.createOneForUser(null);
        TankSnap enemy = TankSnapFactory.createOneForUser(null);

        Double distance = shootingService.calcDistanceBetweenDots(shooterCoord, enemyCoord);
        Double dphi = shootingService.calcDeltaPhi(distance, 100.D);

        shooter.setPlatform(shooterCoord);
        enemy.setPlatform(enemyCoord);

        shooter.setTurretAngle(clientTurretAngle);

        Line line = shooter.toLine();
        line.setAbsoluteAngleRad(line.getAbsoluteAngleRad() + dphi);

        Boolean ok = shootingService.isIntersect(line, enemy);
        Assert.assertEquals(true, ok);
    }

    @Test
    public void IntersectMustInLeft() {

        Coordinate shooterCoord = new Coordinate(0.D, 0.D);
        Coordinate enemyCoord = new Coordinate(1000.D, 1000.D);
        Double clientTurretAngle = 45.D;

        TankSnap shooter = TankSnapFactory.createOneForUser(null);
        TankSnap enemy = TankSnapFactory.createOneForUser(null);

        Double distance = shootingService.calcDistanceBetweenDots(shooterCoord, enemyCoord);
        Double dphi = shootingService.calcDeltaPhi(distance, 100.D);

        shooter.setPlatform(shooterCoord);
        enemy.setPlatform(enemyCoord);

        shooter.setTurretAngle(clientTurretAngle);

        Line line = shooter.toLine();
        line.setAbsoluteAngleRad(line.getAbsoluteAngleRad() - dphi);

        Boolean ok = shootingService.isIntersect(line, enemy);
        Assert.assertEquals(true, ok);
    }


    @Test
    public void NotRightIntersect() {

        Coordinate shooterCoord = new Coordinate(0.D, 0.D);
        Coordinate enemyCoord = new Coordinate(1000.D, 1000.D);
        Double clientTurretAngle = 45.D;

        TankSnap shooter = TankSnapFactory.createOneForUser(null);
        TankSnap enemy = TankSnapFactory.createOneForUser(null);

        Double distance = shootingService.calcDistanceBetweenDots(shooterCoord, enemyCoord);
        Double dphi = shootingService.calcDeltaPhi(distance, 100.D);

        shooter.setPlatform(shooterCoord);
        enemy.setPlatform(enemyCoord);

        shooter.setTurretAngle(clientTurretAngle);

        Line line = shooter.toLine();
        line.setAbsoluteAngleRad(line.getAbsoluteAngleRad() + dphi + STEP);

        Boolean ok = shootingService.isIntersect(line, enemy);
        Assert.assertEquals(false, ok);
    }

    @Test
    public void NotLeftIntersect() {

        Coordinate shooterCoord = new Coordinate(0.D, 0.D);
        Coordinate enemyCoord = new Coordinate(1000.D, 1000.D);
        Double clientTurretAngle = 45.D;

        TankSnap shooter = TankSnapFactory.createOneForUser(null);
        TankSnap enemy = TankSnapFactory.createOneForUser(null);

        Double distance = shootingService.calcDistanceBetweenDots(shooterCoord, enemyCoord);
        Double dphi = shootingService.calcDeltaPhi(distance, 100.D);

        shooter.setPlatform(shooterCoord);
        enemy.setPlatform(enemyCoord);

        shooter.setTurretAngle(clientTurretAngle);

        Line line = shooter.toLine();
        line.setAbsoluteAngleRad(line.getAbsoluteAngleRad() - dphi - STEP);

        Boolean ok = shootingService.isIntersect(line, enemy);
        Assert.assertEquals(false, ok);
    }
}
