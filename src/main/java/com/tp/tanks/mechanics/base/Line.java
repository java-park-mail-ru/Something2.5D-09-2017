package com.tp.tanks.mechanics.base;

public class Line {

    private Long userId;
    private Coordinate dot;
    private Double clientAngleDeg;
    private Double serverAngleRad;


    public Line(Long userId, Coordinate dot, double clientDegree) {
        this.userId = userId;
        this.dot = dot;
        this.clientAngleDeg = clientDegree;

        initServerAngleRadian();
    }

    public Long getUserId() {
        return userId;
    }

    public Coordinate getDot() {
        return dot;
    }

    public Double getClientAngleDeg() {
        return clientAngleDeg;
    }

    public Double getServerAngleRad() {
        return serverAngleRad;
    }

    public void setServerAngleRad(Double serverAngleRad) {
        this.serverAngleRad = serverAngleRad;
    }

    private void initServerAngleRadian() {
        if (clientAngleDeg <= 0) {
            serverAngleRad = Math.toRadians(clientAngleDeg * -1.D);
        } else {
            serverAngleRad = 2 * Math.PI + Math.toRadians(clientAngleDeg * -1.D);
        }
    }

    @Override
    public String toString() {
        return '('
                + "userId: " + userId.toString()
                + ", position: " + dot.toString()
                + ", clientAngleDeg: " + clientAngleDeg.toString()
                + ", serverAngleDeg: " + Math.toDegrees(serverAngleRad)
                + ", serverAngleRad: " + serverAngleRad.toString()
                + '}';
    }
}
