package com.tp.tanks.stubs;

import com.tp.tanks.mechanics.base.Coordinate;

import java.util.Random;

public class CoordinateFactory {

    public static Coordinate create() {
        double startVal = 0;
        double endVal = 10000;

        double random = new Random().nextDouble();
        double valX = startVal + (random * (endVal - startVal));

        random = new Random().nextDouble();
        double valY = startVal + (random * (endVal - startVal));

        return new Coordinate(valX, valY);
    }

}
