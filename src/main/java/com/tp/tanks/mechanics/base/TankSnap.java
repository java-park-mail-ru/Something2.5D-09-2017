package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import com.tp.tanks.websocket.Message;


@SuppressWarnings({"NullableProblems"})
public class TankSnap extends Message {

    @NotNull
    private Coordinate platform;

    @NotNull
    private double platformAngle;

    @NotNull
    private double turretAngle;

    private boolean isShoot;

    @NotNull
    private Long userId;


    @NotNull
    public Coordinate getPlatform() {
        return platform;
    }

    public void setPlatform(@NotNull Coordinate platform) {
        this.platform = platform;
    }

    @JsonProperty("isShoot")
    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }

    @NotNull
    public double getPlatformAngle() {
        return platformAngle;
    }

    public void setPlatformAngle(double platformAngle) {
        this.platformAngle = platformAngle;
    }

    @NotNull
    public double getTurretAngle() {
        return turretAngle;
    }

    public void setTurretAngle(double turretAngle) {
        this.turretAngle = turretAngle;
    }

    @NotNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return '{'
                + "platform: " + platform.toString()
                + ", platformAngle: "  + platformAngle
                + ", turretAngle: " + turretAngle
                + ", isShoot: " + isShoot
                + ", userId: " + userId.toString()
                + '}';
    }

}
