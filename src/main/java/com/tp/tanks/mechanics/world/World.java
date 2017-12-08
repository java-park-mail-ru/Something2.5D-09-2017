package com.tp.tanks.mechanics.world;

import com.tp.tanks.mechanics.base.Coordinate;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class World {
    private ArrayList<Box> boxes;
    private ArrayList<Coordinate> allSpawnPoints;
    private ArrayList<Coordinate> currentSpawnPoints;

    public World() {
        this.boxes = Loader.loadBoxes();
        this.allSpawnPoints = Loader.loadSpawnPoints();
        this.currentSpawnPoints = new ArrayList<>(this.allSpawnPoints);
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public ArrayList<Coordinate> getAllSpawnPoints() {
        return allSpawnPoints;
    }

    public Coordinate getTanksPosition() {
        if (currentSpawnPoints.size() == 0) {
            this.currentSpawnPoints = new ArrayList<>(this.allSpawnPoints);
        }

        int numberOfPosition = ThreadLocalRandom.current().nextInt(0, currentSpawnPoints.size());
        Coordinate tankPosition = currentSpawnPoints.get(numberOfPosition);
        currentSpawnPoints.remove(tankPosition);
        return tankPosition;
    }
}
