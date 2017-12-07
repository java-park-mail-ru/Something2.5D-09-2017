package com.tp.tanks.mechanics.base;

public class Line {

    private Long userId;
    private Coordinate dot;
    private Double angleRad;
    private Double angleDeg;

    private Double koefK;
    private Double koefB;

    public Line(Long userId, Coordinate dot, double degree) {
        this.userId = userId;
        this.dot = dot;
        this.angleDeg = degree;
        this.angleRad = toRadian(degree);
        this.koefK = Math.tan(this.angleRad);
        this.koefB = dot.getValY() - koefK * dot.getValX();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Coordinate getDot() {
        return dot;
    }

    public void setDot(Coordinate dot) {
        this.dot = dot;
    }

    public Double getAngleRad() {
        return angleRad;
    }

    public void setAngleRad(Double angle) {
        this.angleRad = angle;
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

    public Double getAngleDeg() {
        return angleDeg;
    }

    private double toRadian(double degree) {
        double result;
        if (degree >= 0) {
            result = Math.toRadians(degree);
        } else {
            result = 2 * Math.PI + Math.toRadians(degree);
        }
        return result;
    }

    //NOT TESTED
    public double toDegree(double radian) {
        double degree;
        if (radian >= 0 && radian <= Math.PI) {
            degree = radian * 180 / Math.PI;
        } else {
            degree = (radian * 180 / Math.PI) - 360;
        }
        return degree;
    }
}
