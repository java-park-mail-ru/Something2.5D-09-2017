package com.tp.tanks.mechanics;

import com.tp.tanks.mechanics.base.Coordinate;

public class Box {
    private Coordinate position;
    private final static Integer width = 100;
    private final static Integer height = 100;
    private Boolean isBulletProof;

    public Box(Coordinate position, Boolean isBulletProof) {
        this.position = position;
        this.isBulletProof = isBulletProof;
    }

    public static Integer getWidth() { return width; }
    public static Integer getHeight() { return height; }
    public Boolean getBulletProof() { return isBulletProof; }
    public Coordinate getPosition() { return position; }
    public void setBulletProof(Boolean bulletProof) { isBulletProof = bulletProof; }
    public void setPosition(Coordinate position) { this.position = position; }
}
