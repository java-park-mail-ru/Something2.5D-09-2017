package com.tp.tanks.mechanics.base;

import com.tp.tanks.mechanics.world.Box;
import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class WorldSnap extends Message {

    @NotNull
    private ArrayList<Box> boxes;

    private ArrayList<Coordinate> spawnPoints;

    @NotNull
    private Coordinate startTankPosition;


    public Coordinate getStartTankPosition() {
        return startTankPosition;
    }

    public void setStartTankPosition(Coordinate startTankPosition) {
        this.startTankPosition = startTankPosition;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    public ArrayList<Coordinate> getSpawnPoints() {
        return spawnPoints;
    }

    public void setSpawnPoints(ArrayList<Coordinate> spawnPoints) {
        this.spawnPoints = spawnPoints;
    }
}
