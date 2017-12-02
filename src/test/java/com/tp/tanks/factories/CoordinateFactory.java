package com.tp.tanks.factories;

import com.tp.tanks.mechanics.base.Coordinate;


public class CoordinateFactory {

    public static Coordinate create() {

        double valX = Generators.generateDouble();
        double valY = Generators.generateDouble();

        return new Coordinate(valX, valY);
    }

}
