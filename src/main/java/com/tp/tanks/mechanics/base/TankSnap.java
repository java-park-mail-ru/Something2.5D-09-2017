package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import com.tp.tanks.websocket.Message;

/**
 * Created by Solovyev on 03/11/2016.
 */
@SuppressWarnings({"NullableProblems"})
public class TankSnap extends Message {

    private @NotNull Coords platform;
    private @NotNull Coords turret;

    private boolean isShoot;
    private long frameTime;


    public @NotNull Coords getPlatform() {
        return platform;
    }

    public void setPlatform(@NotNull Coords platform) {
        this.platform = platform;
    }

    public long getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(long frameTime) {
        this.frameTime = frameTime;
    }

    public @NotNull Coords getTurret() {
        return turret;
    }

    public void setTurret(Coords turret) {
        this.turret = turret;
    }

    @JsonProperty("isShoot")
    public boolean isShoot() {
        return isShoot;
    }

    public void setShoot(boolean shoot) {
        isShoot = shoot;
    }
}
