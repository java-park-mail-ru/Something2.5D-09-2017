package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;


public class Coordinate {

    private final Double valX;
    private final Double valY;

    public Coordinate(@JsonProperty("valX") Double valX, @JsonProperty("valY") Double valY) {
        this.valX = valX;
        this.valY = valY;
    }

    public Double getValX() {
        return valX;
    }

    public Double getValY() {
        return valY;
    }

    @Override
    public String toString() {
        return '{' + "x: " + valX.toString() + ", y: " + valY.toString() + '}';
    }

    @SuppressWarnings("NewMethodNamingConvention")
    @NotNull
    public static Coordinate of(Double valX, Double valY) {
        return new Coordinate(valX, valY);
    }
}
