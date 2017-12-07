package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class ShootingService {

    private static final double DELTA = 1e-15;

    @NotNull
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShootingService.class);

    public void process(List<TankSnap> snaps, List<Line> lines) {

        if (lines.size() == 0) {
            return;
        }

        for (Line line: lines) {

            LOGGER.info("[ShootingService.process] line: " +  line.toString());

            for (TankSnap snap: snaps) {

                if (Objects.equals(line.getUserId(), snap.getUserId())) {
                    snap.setShoot(true);
                    snap.setTurretAngle(line.getClientAngleDeg());
                    continue;
                }

                if (isIntersect(line, snap)) {
                    LOGGER.info("[ShootingService.process] isIntersect == true");
                    if (snap.getHealth() != null) {
                        snap.setHealth(snap.getHealth() - 10);
                    }
                }
            }
        }
    }

    public Double calcDistanceBetweenDots(Coordinate first, Coordinate second) {
        return Math.sqrt(Math.pow((first.getValX() - second.getValX()), 2) + Math.pow((first.getValY() - second.getValY()), 2));
    }

    public Double calcDeltaPhi(Double distance, Double radius) {
        return Math.atan(radius / distance);
    }

    public Double calcAngleBetweenDots(Coordinate left, Coordinate right) {
        Double dx = right.getValX() - left.getValX();
        Double dy = right.getValY() - left.getValY();

        if (Math.abs(dx) <= DELTA) {
            if (dy >= 0) {
                return Math.PI / 2;
            } else {
                return  3 * Math.PI / 2;
            }
        }

        if (dx >= 0.D && dy >= 0.D) {
            return  Math.atan(dy / dx);

        } else if (dx >= 0.D && dy <= 0.D) {
            return 2 * Math.PI + Math.atan(dy / dx);

        } else if (dx <= 0.D && dy >= 0.D) {
            return Math.PI + Math.atan(dy / dx);

        } else {
            return Math.PI + Math.atan(dy / dx);
        }
    }

    public Boolean isIntersect(Line line, TankSnap snap) {
        Double distance = calcDistanceBetweenDots(snap.getPlatform(), line.getDot());
        Double dpdhi = calcDeltaPhi(distance, 100.D);
        Double phi = calcAngleBetweenDots(line.getDot(), snap.getPlatform());

//
//        LOGGER.info("Distance = " + distance.toString());
//        LOGGER.info("dPhi = " + dpdhi.toString());
//        LOGGER.info("phi = " + phi.toString());
//        LOGGER.info("line angle = " + line.getAbsoluteAngleRad().toString());


        return line.getAbsoluteAngleRad() <= phi + dpdhi && line.getAbsoluteAngleRad() >= phi - dpdhi;
    }
}
