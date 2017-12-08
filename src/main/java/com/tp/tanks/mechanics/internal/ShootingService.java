package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.world.Box;
import com.tp.tanks.mechanics.world.World;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.*;

public class ShootingService {

    private static final double DELTA = 1e-15;

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(ShootingService.class);

    @NotNull
    private World world;

    @NotNull
    private ArrayList<Box> boxes;

    public ShootingService() {
        this.world = new World();
        this.boxes = world.getBoxes();
    }


    public void process(List<TankSnap> snaps, List<Line> lines) {

        if (lines.size() == 0) {
            return;
        }

        for (Line line: lines) {

            ArrayList<TankSnap> intersectSnaps = new ArrayList<>();

            for (TankSnap snap: snaps) {
                if (Objects.equals(line.getUserId(), snap.getUserId())) {
                    snap.setShoot(true);
                    snap.setTurretAngle(line.getClientAngleDeg());
                    continue;
                }

                if (isIntersect(line, snap.getPlatform())) {
                    intersectSnaps.add(snap);
                }
            }

            ArrayList<Box> intersectBoxes = new ArrayList<>();
            for(Box box: this.boxes) {
                if (isIntersect(line, box.getPosition())) {
                    intersectBoxes.add(box);
                }
            }

            TankSnap closestSnap = getClosestTank(intersectSnaps, line);
            Box closestBox = getClosestBox(intersectBoxes, line);
            if (closestSnap != null) {
                if(closestBox != null) {
                    if(compareTankAndBox(closestSnap, closestBox, line)) { ;
                        closestSnap.setHealth(closestSnap.getHealth() - 10);
                    }
                } else {
                    closestSnap.setHealth(closestSnap.getHealth() - 10);
                }

            }
        }
    }

    private boolean compareTankAndBox(TankSnap tankSnap, Box box, Line line) {
        Double tankDist = calcDistanceBetweenDots(tankSnap.getPlatform(), line.getDot());
        Double boxDist = calcDistanceBetweenDots(box.getPosition(), line.getDot());
        return tankDist < boxDist;
    }

    private Box getClosestBox(ArrayList<Box> boxesToCompare, Line line) {
        if(boxesToCompare.size() == 0) {
            return null;
        }
        Comparator<Box> distanceComparator = (box1, box2) -> {
            double distance1 = calcDistanceBetweenDots(line.getDot(), box1.getPosition());
            double distance2 = calcDistanceBetweenDots(line.getDot(), box2.getPosition());
            return (int) (distance1 - distance2);
        };

        return boxesToCompare.stream().min(distanceComparator).get();
    }

    public TankSnap getClosestTank(ArrayList<TankSnap> snaps, Line line) {
        if (snaps.size() == 0) {
            return null;
        }
        Comparator<TankSnap> distanceComparator = (snap1, snap2) -> {
            double distance1 = calcDistanceBetweenDots(line.getDot(), snap1.getPlatform());
            double distance2 = calcDistanceBetweenDots(line.getDot(), snap2.getPlatform());
            return (int) (distance1 - distance2);
        };

        return snaps.stream().min(distanceComparator).get();
    }


    public Double calcDistanceBetweenDots(Coordinate first, Coordinate second) {
        return Math.sqrt(Math.pow((first.getValY() - second.getValY()), 2)
                + Math.pow((first.getValX() - second.getValX()), 2));
    }

    public Double calcDeltaPhi(Double distance, Double radius) {
        return Math.atan(radius / distance);
    }

    public Double calcAngleBetweenDots(Coordinate left, Coordinate right) {
        Double dx = right.getValX() - left.getValX();
        Double dy = right.getValY() - left.getValY();
        dy = changeSign(dy);    // for X-axis reflection, client have negative Y-axis

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

    public Boolean isIntersect(Line line, Coordinate coordinate) {
        Double distance = calcDistanceBetweenDots(coordinate, line.getDot());
        Double dpdhi = calcDeltaPhi(distance, 32.D);

        Double angleBetweenDots = calcAngleBetweenDots(line.getDot(), coordinate);

        LOGGER.info("line = " + line.toString());
        LOGGER.info("Distance = " + distance.toString());
        LOGGER.info("dPhi = " + dpdhi.toString() + " [rad]");
        LOGGER.info("angleBetweenDots = " + angleBetweenDots.toString() + " [rad]");
        LOGGER.info("absolute angle line = " + line.getServerAngleRad().toString() + " [rad]");


        return line.getServerAngleRad() <= angleBetweenDots + dpdhi && line.getServerAngleRad() >= angleBetweenDots - dpdhi;
    }

    private Double changeSign(Double var) {
        return var * -1;
    }
}
