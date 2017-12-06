package com.tp.tanks.mechanics.base;

public class Line {

    private Long userId;
    private Coordinate dot;
    private Double angle;

    private Double koefK;
    private Double koefB;

    public Line(Long userId, Coordinate dot, double angle) {
        this.userId = userId;
        this.dot = dot;
        this.angle = convert(angle);
        this.koefK = Math.tan(this.angle);
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
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


    private double convert(double angle) {
        double result;
        if (angle >= 0) {
            result = Math.toRadians(angle);
        } else {
            result = 2 * Math.PI + Math.toRadians(angle);
        }
        return result;
    }
}
