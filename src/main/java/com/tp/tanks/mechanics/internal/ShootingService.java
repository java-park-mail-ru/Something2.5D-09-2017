package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShootingService {

    private static final double DELTA = 1e-15;

    @NotNull
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShootingService.class);

    public void process(List<TankSnap> snaps, List<Line> lines) {

        if (lines.size() == 0) {
            return;
        }

        for (Line line: lines) {

            ArrayList<TankSnap> intersectSnaps = new ArrayList<>();
            LOGGER.info("[ShootingService.process] line: " +  line.toString());

            for (TankSnap snap: snaps) {

                if (Objects.equals(line.getUserId(), snap.getUserId())) {
                    snap.setShoot(true);
                    snap.setTurretAngle(line.getClientAngleDeg());
                    continue;
                }

                LOGGER.info("[ShootingService.process] snap: " +  snap.toString());

                if (isIntersect(line, snap)) {
                    LOGGER.info("[ShootingService.process] isIntersect == true");
                    intersectSnaps.add(snap);
                }
            }

            TankSnap closestSnap = getClosestTank(intersectSnaps, line);
            if (closestSnap.getHealth() != null) {
                closestSnap.setHealth(closestSnap.getHealth() - 10);
            }

        }
    }

    public TankSnap getClosestTank(ArrayList<TankSnap> snaps, Line line1) {
        if(snaps.size() == 0) {
            return null;
        }
        Comparator<TankSnap> distanceComparator = new Comparator<TankSnap>() {
            @Override
            public int compare(TankSnap snap1, TankSnap snap2) {
                double distance1 = calcDistanceBetweenDots(line1.getDot(), snap1.getPlatform());
                double distance2 = calcDistanceBetweenDots(line1.getDot(), snap2.getPlatform());
                return (int)(distance1 - distance2);
            }
        };

        return snaps.stream().min(distanceComparator).get();

//        snaps.sort(distanceComparator);
//        return snaps.get(0);
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
        Double dpdhi = calcDeltaPhi(distance, 32.D);

        Coordinate serverLineDot = toServerCoordinate(line.getDot());
        Coordinate serverPlatform = toServerCoordinate(snap.getPlatform());

        Double angleBetweenDots = calcAngleBetweenDots(serverLineDot, serverPlatform);


        LOGGER.info("serverLineDot = " + serverLineDot.toString());
        LOGGER.info("serverPlatform = " + serverPlatform.toString());
        LOGGER.info("Distance = " + distance.toString());
        LOGGER.info("dPhi = " + dpdhi.toString() + " [rad]");
        LOGGER.info("angleBetweenDots = " + angleBetweenDots.toString() + " [rad]");
        LOGGER.info("absolute angle line = " + line.getAbsoluteAngleRad().toString() + " [rad]");


        return line.getAbsoluteAngleRad() <= angleBetweenDots + dpdhi && line.getAbsoluteAngleRad() >= angleBetweenDots - dpdhi;
    }

    private Coordinate toServerCoordinate(Coordinate clientCoordinate) {
        return new Coordinate(clientCoordinate.getValX(), - clientCoordinate.getValY());
    }
}
