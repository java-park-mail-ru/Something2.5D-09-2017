package com.tp.tanks.mechanics.base;

class Line {

    private Long userId;
    private Coordinate startDot;
    private Double turretAngle;

    private Double koefK;
    private Double koefB;

    Line(Long userId, Coordinate startDot, double turretAngle) {
        this.userId = userId;
        this.startDot = startDot;
        this.turretAngle = turretAngle;
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

    public void setTurretAngle(Double turretAngle) {
        this.turretAngle = turretAngle;
    }

    public Double getKoefK() {
        return koefK;
    }

    public void setKoefK(Double koefK) {
        this.koefK = koefK;
    }

    public double getKoefB() {
        return koefB;
    }

    public void setKoefB(Double koefB) {
        this.koefB = koefB;
    }
}
