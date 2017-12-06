package com.tp.tanks.mechanics.Map;

import com.tp.tanks.mechanics.Map.Box;
import com.tp.tanks.mechanics.base.Coordinate;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;

public class GameObjects {
    private ArrayList<Box> boxes;
    private ArrayList<Coordinate> spawnPositionsForBoxes;
    private ArrayList<Coordinate> spawnPositionsForTanks;

    private final Integer maxPositionX = 3840;
    private final Integer maxPositionY = 2160;

    private final Integer amountOfBoxPositions = 10;
    private final Integer amountOfTankPositions = 10;

    public GameObjects(ArrayList<Box> boxes) {
        this.boxes = boxes;
        this.spawnPositionsForBoxes = new ArrayList<>();
        this.spawnPositionsForTanks = new ArrayList<>();
        generatePositions(spawnPositionsForBoxes, amountOfBoxPositions);
        generatePositions(spawnPositionsForTanks, amountOfTankPositions);
    }

    private void generatePositions(ArrayList<Coordinate> spawnPositions, Integer amount) {
        for(int i = 0; i < amount; ++i) {
            Coordinate position = generatePosition();
            if(checkPosition(spawnPositionsForBoxes, position) && checkPosition(spawnPositionsForTanks, position)) {
                spawnPositions.add(position);
            }
        }
    }

    private Coordinate generatePosition() {
        double valX = ThreadLocalRandom.current().nextDouble(Box.getWidth(), maxPositionX - Box.getWidth());
        double valY = ThreadLocalRandom.current().nextDouble(Box.getHeight(), maxPositionY - Box.getHeight());
        return new Coordinate(valX, valY);
    }

    private boolean checkPosition(ArrayList<Coordinate> spawnPositions, Coordinate newPosition) {
        Rectangle newObjectOnMap = new Rectangle((int)newPosition.getValX(), (int)newPosition.getValY(), Box.getWidth(), Box.getHeight());

        for(Coordinate position: spawnPositions) {
            Rectangle objectOnMap = new Rectangle((int)position.getValX(), (int)position.getValY(), Box.getWidth(), Box.getHeight());
            if(objectOnMap.intersects(newObjectOnMap)) {
                return false;
            }
        }
        return true;
    }
}
