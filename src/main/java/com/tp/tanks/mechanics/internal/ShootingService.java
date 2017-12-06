package com.tp.tanks.mechanics.internal;

import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.Line;
import com.tp.tanks.mechanics.base.TankSnap;

import java.util.List;
import java.util.Objects;

public class ShootingService {

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

//    private double getВiscriminant(Double k, Double b, Double r) {
//        return (1 + k * k) - (b * b - r * r);
//    }
//
//
//    public boolean isIntersect() {
//        return false;
//    }
//
//
//    private Double getNormalizedKoefB(Double b, Coordinate dot) {
//        Double norm_b = b - (dot.getValY());
//    }
}
