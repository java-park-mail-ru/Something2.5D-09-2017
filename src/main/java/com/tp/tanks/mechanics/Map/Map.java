package com.tp.tanks.mechanics.Map;

import com.tp.tanks.mechanics.base.Coordinate;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Map {
    private ArrayList<Box> boxes;
    private ArrayList<Coordinate> spawnPositionsForTanks;

    private final int amountOfBoxes = 9;
    private final int amountOfTankPositions = 4;

    public Map() {
        this.boxes = new ArrayList<>();
        this.spawnPositionsForTanks = new ArrayList<>();
        fillBoxes();
        fillSpawnPositionsForTanks();
    }

    public ArrayList<Box> getBoxes() { return boxes; }
    public void setBoxes(ArrayList<Box> boxes) { this.boxes = boxes; }

    public Coordinate getTanksPosition() {
        int numberOfPosition = ThreadLocalRandom.current().nextInt(0, amountOfTankPositions);

        Coordinate tankPosition = spawnPositionsForTanks.get(numberOfPosition);
        spawnPositionsForTanks.remove(tankPosition);
        return tankPosition;
    }

    private void fillBoxes() {

        Box box1 = new Box(new Coordinate(670, 250), false);
        Box box2 = new Box(new Coordinate(815, 395), false);
        Box box3 = new Box(new Coordinate(960, 540), false);
        Box box4 = new Box(new Coordinate(1105, 395), false);
        Box box5 = new Box(new Coordinate(1250, 250), false);
        Box box6 = new Box(new Coordinate(670, 830), false);
        Box box7 = new Box(new Coordinate(815, 685), false);
        Box box8 = new Box(new Coordinate(1105, 685), false);
        Box box9 = new Box(new Coordinate(1250, 830), false);

        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        boxes.add(box5);
        boxes.add(box6);
        boxes.add(box7);
        boxes.add(box8);
        boxes.add(box9);
    }

    private void fillSpawnPositionsForTanks() {
        Coordinate tankPosition1 = new Coordinate(525, 105);
        Coordinate tankPosition2 = new Coordinate(525, 975);
        Coordinate tankPosition3 = new Coordinate(1395, 105);
        Coordinate tankPosition4 = new Coordinate(1395, 975);

        spawnPositionsForTanks.add(tankPosition1);
        spawnPositionsForTanks.add(tankPosition2);
        spawnPositionsForTanks.add(tankPosition3);
        spawnPositionsForTanks.add(tankPosition4);
    }
}
