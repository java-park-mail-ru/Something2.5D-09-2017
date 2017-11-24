package com.tp.tanks.mechanics.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("PublicField")
public class Coordinate {

    private final double valX;
    private final double valY;

    public Coordinate(@JsonProperty("valX") double valX, @JsonProperty("valY") double valY) {
        this.valX = valX;
        this.valY = valY;
    }

    public String X(){
        Double value = valX;
        return value.toString();
    }


    public String Y(){
        Double value = valY;
        return value.toString();
    }

    @Override
    public String toString() {
        return '{' + "valX=" + valX + ", valY=" + valY + '}';
    }

    @SuppressWarnings("NewMethodNamingConvention")
    @NotNull
    public static Coordinate of(double valX, double valY) {
        return new Coordinate(valX, valY);
    }
}
