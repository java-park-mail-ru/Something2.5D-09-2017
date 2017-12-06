package com.tp.tanks.mechanics.world;

import com.tp.tanks.mechanics.base.Coordinate;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class World {
    private ArrayList<Box> boxes;
    private ArrayList<Coordinate> spawnPoints;

    public World() {
        this.boxes = Loader.loadBoxes();
        this.spawnPoints = Loader.loadSpawnPoints();
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public ArrayList<Coordinate> getSpawnPoints() {
        return spawnPoints;
    }

    public Coordinate getTanksPosition() {
        int numberOfPosition = ThreadLocalRandom.current().nextInt(0, spawnPoints.size());
        Coordinate tankPosition = spawnPoints.get(numberOfPosition);
        spawnPoints.remove(tankPosition);
        return tankPosition;
    }
}
