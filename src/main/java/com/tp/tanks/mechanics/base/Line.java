package com.tp.tanks.mechanics.base;

public class Line {

    private Long userId;
    private Coordinate dot;
    private Double clientAngleDeg;
    private Double serverAngleDeg;

    private Double absoluteAngleRad;

    private Double koefK;
    private Double koefB;

    public Line(Long userId, Coordinate dot, double clientDegree) {
        this.userId = userId;
        this.dot = dot;
        this.clientAngleDeg = clientDegree;

        initServerAngleDegree();
        initServerAngleRadian();

        this.koefK = Math.tan(this.absoluteAngleRad);
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

    public Double getAbsoluteAngleRad() {
        return absoluteAngleRad;
    }

    public void setAbsoluteAngleRad(Double angle) {
        this.absoluteAngleRad = angle;
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

    public Double getClientAngleDeg() {
        return clientAngleDeg;
    }

    public Double getServerAngleDeg() {
        return serverAngleDeg;
    }


    private void initServerAngleDegree() {
        serverAngleDeg = clientAngleDeg * -1;
    }

    private void initServerAngleRadian() {
        if (serverAngleDeg >= 0) {
            absoluteAngleRad = Math.toRadians(serverAngleDeg);
        } else {
            absoluteAngleRad = 2 * Math.PI + Math.toRadians(serverAngleDeg);
        }
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

    @Override
    public String toString() {
        return '('
                + "userId: " + userId.toString()
                + ", clientAngleDeg: " + clientAngleDeg.toString()
                + ", serverAngleDeg: " + serverAngleDeg.toString()
                + ", absoluteAngleDeg: " + Math.toDegrees(absoluteAngleRad)
                + ", absoluteAngleRad: " + absoluteAngleRad.toString()
                + '}';
    }


}
