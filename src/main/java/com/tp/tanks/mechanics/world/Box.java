package com.tp.tanks.mechanics.world;

import com.tp.tanks.mechanics.base.Coordinate;

public class Box {
    private Coordinate position;
    private static final  Integer WIDTH = 100;
    private static final  Integer HEIGHT = 100;
    private Boolean isBulletProof;

    public Box(Coordinate position, Boolean isBulletProof) {
        this.position = position;
        this.isBulletProof = isBulletProof;
    }

    public static Integer getWidth() {
        return WIDTH;
    }

    public static Integer getHeight() {
        return HEIGHT;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Boolean getBulletProof() {
        return isBulletProof;
    }

    public void setBulletProof(Boolean bulletProof) {
        isBulletProof = bulletProof;
    }

    @Override
    public String toString() {
        return '{'
                + "statistic: "  + position.toString()
                + ", isBulletProof: " + isBulletProof.toString()
                + '}';
    }
}
