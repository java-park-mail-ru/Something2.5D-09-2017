package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("PublicField")
public class Coords {

    public final double x;
    public final double y;
    public final double phi;

    public Coords(@JsonProperty("x") double x, @JsonProperty("y") double y, @JsonProperty("y") double phi) {
        this.x = x;
        this.y = y;
        this.phi = phi;
    }


    @Override
    public String toString() {
        return '{' +
                "x=" + x +
                ", y=" + y +
                ", phi=" + phi +
                '}';
    }
    @SuppressWarnings("NewMethodNamingConvention")
    public static @NotNull Coords of(double x, double y, double phi) {
        return new Coords(x, y, phi);
    }
}
