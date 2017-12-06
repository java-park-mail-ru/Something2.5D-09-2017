package com.tp.tanks.mechanics.base;

class Line {

    private Long userId;
    private Coordinate startDot;
    private double turretAngle;

    private double koefK;
    private double koefB;

    Line(Long userId, Coordinate startDot, double turretAngle) {
        this.userId = userId;
        this.startDot = startDot;
        this.turretAngle = turretAngle;
        estimateLine(startDot, turretAngle);
    }

    private void estimateLine(Coordinate startDot, double turretAngle) {
        this.koefK = Math.tan(turretAngle);
        this.koefB = startDot.getValY() - koefK * startDot.getValX();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Coordinate getStartDot() {
        return startDot;
    }

    public void setStartDot(Coordinate startDot) {
        this.startDot = startDot;
    }

    public double getTurretAngle() {
        return turretAngle;
    }

    public void setTurretAngle(double turretAngle) {
        this.turretAngle = turretAngle;
    }

    public double getKoefK() {
        return koefK;
    }

    public void setKoefK(double koefK) {
        this.koefK = koefK;
    }

    public double getKoefB() {
        return koefB;
    }

    public void setKoefB(double koefB) {
        this.koefB = koefB;
    }
}
