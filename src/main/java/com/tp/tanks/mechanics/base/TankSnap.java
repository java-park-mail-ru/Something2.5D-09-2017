package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import com.tp.tanks.websocket.Message;

/**
 * Created by Solovyev on 03/11/2016.
 */
@SuppressWarnings({"NullableProblems"})
public class TankSnap extends Message {

    @NotNull
    private Coordinate platform;

    @NotNull
    private double platformAngle;

    @NotNull
    private double turretAngle;

    private boolean isShoot;
    private long frameTime;


    @NotNull
    public Coordinate getPlatform() {
        return platform;
    }

    public void setPlatform(@NotNull Coordinate platform) {
        this.platform = platform;
    }

    public long getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(long frameTime) {
        this.frameTime = frameTime;
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
}
