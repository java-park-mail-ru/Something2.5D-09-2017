package com.tp.tanks.mechanics.base;

public class Line {

    private Long userId;
    private Coordinate startDot;
    private double turretAngle;

    private double koefK;
    private double koefB;

    public Line(Long userId, Coordinate startDot, double turretAngle) {
        this.userId = userId;
        this.startDot = startDot;
        this.turretAngle = turretAngle;


    }

    private void estimateLine(Coordinate startDot, double turretAngle) {
        koefK = Math.tan(turretAngle);
        koefB = startDot.getValY() - koefK * startDot.getValX();
    }
}
