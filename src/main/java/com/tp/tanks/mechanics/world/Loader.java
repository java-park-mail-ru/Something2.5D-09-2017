package com.tp.tanks.mechanics.world;

import java.io.*;
import java.util.ArrayList;

import com.tp.tanks.mechanics.base.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class Loader {
    private static final Integer CURRENT_MAP = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);

    public static ArrayList<Box> loadBoxes() {

        ArrayList<Box> data = new ArrayList<>();

        try {

            final Resource resource = new ClassPathResource("world/map" + CURRENT_MAP.toString() + "/boxes.csv");
            final InputStream resourceinputStream = resource.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceinputStream));

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

            final Resource resource = new ClassPathResource("world/map" + CURRENT_MAP.toString() + "/spawnPoints.csv");
            final InputStream resourceinputStream = resource.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceinputStream));

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
