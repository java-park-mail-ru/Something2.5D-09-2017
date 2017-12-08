package com.tp.tanks.mechanics.world;

import java.io.*;
import java.util.ArrayList;

import com.tp.tanks.mechanics.base.Coordinate;


public class Loader {
    private static final Integer CURRENT_MAP = 1;

    public static ArrayList<Box> loadBoxes() {

        ArrayList<Box> data = new ArrayList<>();

        try {

            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("world/map" + CURRENT_MAP.toString() + "/boxes.csv").getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            int iteration = 0;

            while ((line = reader.readLine()) != null) {

                if (iteration == 0) {
                    iteration++;
                    continue;
                }

                String[] row = line.split(",");
                if (row.length == 0) {
                    continue;
                }

                Double valX = Double.parseDouble(row[0]);
                Double valY = Double.parseDouble(row[1]);
                Boolean isBulletproof = Boolean.parseBoolean(row[2]);
                Box box = new Box(new Coordinate(valX, valY), isBulletproof);
                data.add(box);

            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return data;
    }


    public static ArrayList<Coordinate> loadSpawnPoints() {

        ArrayList<Coordinate> data = new ArrayList<>();

        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("world/map"  + CURRENT_MAP.toString() + "/spawnPoints.csv").getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            int iteration = 0;

            while ((line = reader.readLine()) != null) {

                if (iteration == 0) {
                    iteration++;
                    continue;
                }

                String[] row = line.split(",");
                if (row.length == 0) {
                    continue;
                }

                Double valX = Double.parseDouble(row[0]);
                Double valY = Double.parseDouble(row[1]);
                data.add(new Coordinate(valX, valY));

            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return data;
    }
}
