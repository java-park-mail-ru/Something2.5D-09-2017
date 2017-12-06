package com.tp.tanks.factories;

import com.tp.tanks.mechanics.base.TankSnap;

import java.util.ArrayList;
import java.util.List;


public class TankSnapFactory {

    public static List<TankSnap> createManyForUser(Long userId, int amount) {
        List<TankSnap> tanksSnaps = new ArrayList<>();
        for(int i = 0; i < amount; ++i) {
            tanksSnaps.add(createOneForUser(userId));
        }
        return tanksSnaps;
    }

    public static TankSnap createOneForUser(Long userId) {
        TankSnap tankSnap = new TankSnap();

        tankSnap.setPlatform(CoordinateFactory.create());
        tankSnap.setPlatformAngle(Generators.generateDouble());
        tankSnap.setTurretAngle(Generators.generateDouble());
        tankSnap.setUsername(Generators.generateString(15));
        tankSnap.setShoot(Generators.generateBoolean());
        tankSnap.setUserId(userId);

        return tankSnap;
    }
}
