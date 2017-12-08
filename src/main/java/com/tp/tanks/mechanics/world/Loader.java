package com.tp.tanks.mechanics.world;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.tp.tanks.mechanics.base.Coordinate;


public class Loader {
    private static final int AMOUNT_OF_MAPS = 2;
    private static Integer CURRENT_MAP;

    public static ArrayList<Box> loadBoxes() {

        ArrayList<Box> data = new ArrayList<>();

        try {
            CURRENT_MAP = ThreadLocalRandom.current().nextInt(1, AMOUNT_OF_MAPS);
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(classLoader.getResource("world/map" + CURRENT_MAP.toString() + "/boxes.csv").getFile());
//            File file = new File(classLoader.getResource("world/map1/boxes.csv").getFile());
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
//            File file = new File(classLoader.getResource("world/map1/spawnPoints.csv").getFile());
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
