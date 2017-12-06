package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;

import java.util.List;
import java.util.Objects;

public class ShootingService {

    private static final double DELTA = 1e-15;

    public List<TankSnap> handle(List<TankSnap> snaps, List<Line> lines) {

        if (lines.size() == 0) {
            return snaps;
        }

        for (Line line: lines) {


            for (TankSnap snap: snaps) {
                if (Objects.equals(line.getUserId(), snap.getUserId())) {
                    continue;
                }


            }
        }

        return snaps;

    }

    public Double caclDistance(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow((a.getValX() - b.getValX()), 2) + Math.pow((a.getValY() - b.getValY()), 2));
    }

    public Double calcDeltaPhi(Double distance, Double radius) {
        return Math.atan(radius / distance);
    }

    public Double calcAngle(Coordinate first, Coordinate second) {
        Double dx = second.getValX() - first.getValX();
        Double dy = second.getValY() - first.getValY();

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

    public Boolean isIntersect(TankSnap snap, Line line) {
        Double distance = caclDistance(snap.getPlatform(), line.getDot());
        Double dpdhi = calcDeltaPhi(distance, 100.D);
        Double phi = calcAngle(snap.getPlatform(), line.getDot());
        return line.getAngle() <= phi + dpdhi && line.getAngle() >= phi - dpdhi;
    }
}
