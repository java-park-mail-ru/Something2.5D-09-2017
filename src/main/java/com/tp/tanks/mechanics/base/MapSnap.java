package com.tp.tanks.mechanics.base;

import com.tp.tanks.mechanics.Map.Box;
import com.tp.tanks.websocket.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MapSnap extends Message {

    @NotNull ArrayList<Box> boxes;

    @NotNull Coordinate startTankPosition;

    public Coordinate getStartTankPosition() { return startTankPosition; }
    public void setStartTankPosition(Coordinate startTankPosition) { this.startTankPosition = startTankPosition; }

    public ArrayList<Box> getBoxes() { return boxes; }
    public void setBoxes(ArrayList<Box> boxes) { this.boxes = boxes; }
}
